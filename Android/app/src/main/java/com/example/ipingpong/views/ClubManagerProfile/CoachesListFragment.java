package com.example.ipingpong.views.ClubManagerProfile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.SelectUsersByUserTypesCallBack;
import com.example.ipingpong.controllers.UserController;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.shared.entities.UserType;
import com.example.ipingpong.views.Adaptors.Coach_ClubManagerListRecyclerViewAdapter;
import com.example.ipingpong.views.Adaptors.PlayersListRecyclerViewAdapter;
import com.example.ipingpong.views.Adaptors.SwipeToDeleteCallback;
import com.example.ipingpong.views.CoachProfile.CoachProfileFragment;
import com.example.ipingpong.views.PlayerProfile.PlayerProfileFragment;

import java.util.ArrayList;

public class CoachesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private Coach_ClubManagerListRecyclerViewAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Button addCoachBtn;
    private UserController userController;


    public CoachesListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coaches_list, container, false);

        userController = new UserController(getActivity());
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);

        populateRecyclerView();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        addCoachBtn = view.findViewById(R.id.addCoachBtn);
        addCoachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new CoachProfileFragment(getActivity(), Action.Add, null);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }
        });


        //enableSwipeToDeleteAndUndo();

        return view;
    }

    private void populateRecyclerView() {

        userController.getUsersByUserType(String.valueOf(UserType.COACH.getValue()), new SelectUsersByUserTypesCallBack() {

            @Override
            public void onSuccess(ArrayList<User> users) {
                mAdapter = new Coach_ClubManagerListRecyclerViewAdapter(getActivity(), users);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText( getActivity(), reason, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final User user = mAdapter.getData().get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Delete Coach");
                builder.setMessage("Are you sure you want to delete the Coach " + user.getFirstName() + " " + user.getLastName());

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        mAdapter.removeItem(position);

                        userController.deleteUser(user.getId(), new ControlCallBacks() {
                            @Override
                            public void onSuccess(String success) {
                                Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(String reason) {
                                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
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

                /*Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(player, position);
                        recyclerView.scrollToPosition(position);


                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();*/

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

}
