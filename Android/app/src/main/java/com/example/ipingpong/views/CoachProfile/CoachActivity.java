package com.example.ipingpong.views.CoachProfile;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;
import com.example.ipingpong.R;

import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.Base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CoachActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    User user;
    ArrayList<Player> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);

        bottomNavigationView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_Profile, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_Profile)
                .build();

        user = (User) getIntent().getSerializableExtra("User");

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        PlayersListFragment playersListFragment = new PlayersListFragment();
        replaceFragment(playersListFragment, "Player List", false);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.navigation_players:
                PlayersListFragment playersListFragment = new PlayersListFragment();
                replaceFragment(playersListFragment, "Player List", false);
                break;

            case R.id.navigation_Profile:
                CoachProfileFragment coachProfileFragment = new CoachProfileFragment(this, Action.View, user);
                replaceFragment(coachProfileFragment, "Coach Profile", false);
                break;

            case R.id.navigation_dashboard:
                PlayersPerformanceFragment playersPerformanceFragment = new PlayersPerformanceFragment();
                replaceFragment(playersPerformanceFragment, "Player Performance", false);
                break;

            case R.id.navigation_dataset:
                DatasetEntryFragment datasetEntryFragment = new DatasetEntryFragment();
                replaceFragment(datasetEntryFragment, "Dataset Entry", false);
                break;

            case R.id.navigation_notifications:
                MessagesFragment messagesFragment = new MessagesFragment();
                replaceFragment(messagesFragment, "Send Notification", false);
                break;
        }

        return true;

    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}


