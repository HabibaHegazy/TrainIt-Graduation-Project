package com.example.ipingpong.views.AdminProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.SelectUsersByUserTypesCallBack;
import com.example.ipingpong.controllers.UserController;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.shared.entities.UserType;
import com.example.ipingpong.views.Adaptors.Coach_ClubManagerListRecyclerViewAdapter;
import com.example.ipingpong.views.Adaptors.SwipeToDeleteCallback;
import com.example.ipingpong.views.ClubManagerProfile.ClubManagerProfileFragment;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Coach_ClubManagerListRecyclerViewAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Button addClubManagerBtn;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userController = new UserController(this);
        recyclerView = findViewById(R.id.recyclerView_admin);

        populateRecyclerView();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        addClubManagerBtn = findViewById(R.id.addClubManagerBtn);
        addClubManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get to club manager fragment to add club manager
                recyclerView.setVisibility(View.GONE);
                addClubManagerBtn.setVisibility(View.GONE);

                Fragment fragment = new ClubManagerProfileFragment(getApplicationContext(), Action.Add, null);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }
        });

        enableSwipeToDeleteAndUndo();
    }

    private void populateRecyclerView() {

        userController.getUsersByUserType(String.valueOf(UserType.ClubManager.getValue()), new SelectUsersByUserTypesCallBack() {

            @Override
            public void onSuccess(ArrayList<User> users) {
                mAdapter = new Coach_ClubManagerListRecyclerViewAdapter(getApplicationContext(), users);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(AdminActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(AdminActivity.this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final User user = mAdapter.getData().get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

                builder.setTitle("Delete Club Manager and Club");
                builder.setMessage("Are you sure you want to delete the club manager and club " + user.getFirstName() + " " + user.getLastName());

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        mAdapter.removeItem(position);

                        userController.deleteUser(user.getId(), new ControlCallBacks() {
                            @Override
                            public void onSuccess(String success) {
                                Toast.makeText(AdminActivity.this, success, Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(String reason) {
                                Toast.makeText(AdminActivity.this, reason, Toast.LENGTH_LONG).show();
                            }
                        });

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
