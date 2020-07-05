package com.example.ipingpong.views.CoachProfile;

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

import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CoachController;
import com.example.ipingpong.controllers.CallBacks.SelectPlayersCallBack;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.datasource.local.SharedPrefManager;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.R;
import com.example.ipingpong.views.Adaptors.PlayersListRecyclerViewAdapter;
import com.example.ipingpong.views.Adaptors.SwipeToDeleteCallback;
import com.example.ipingpong.views.PlayerProfile.PlayerProfileFragment;

import java.util.ArrayList;

public class PlayersListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlayersListRecyclerViewAdapter mAdapter;
    private CoachController coachController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_players_list, container, false);

        coachController = new CoachController(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);

        populateRecyclerView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        Button addPlayerBtn = view.findViewById(R.id.addPlayerBtn);
        addPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get to player fragment to add player
                PlayerProfileFragment playerProfileFragment = new PlayerProfileFragment(getActivity(), Action.View, null);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new PlayerProfileFragment(getActivity(), Action.Add, null);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }
        });


        enableSwipeToDeleteAndUndo();

        return view;
    }

    private void populateRecyclerView() {

        // from the shared Preferences
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        int currentCoachId = sharedPrefManager.getUser().getId();

        coachController.getPlayersList(currentCoachId, new SelectPlayersCallBack() {
            @Override
            public void onSuccess(ArrayList<Player> players) {
                mAdapter = new PlayersListRecyclerViewAdapter(getActivity(), players);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                Constants.playerArrayList = players;
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Player player = mAdapter.getData().get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Delete Player");
                builder.setMessage("Are you sure you want to delete the player " + player.getUser().getFirstName() + " " + player.getUser().getLastName());

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        mAdapter.removeItem(position);

                        coachController.deletePlayer(player.getId(), new ControlCallBacks() {
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
