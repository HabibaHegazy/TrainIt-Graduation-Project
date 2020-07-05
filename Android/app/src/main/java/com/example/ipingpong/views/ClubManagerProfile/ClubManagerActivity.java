package com.example.ipingpong.views.ClubManagerProfile;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.Base.BaseActivity;
import com.example.ipingpong.views.CoachProfile.PlayersListFragment;
import com.example.ipingpong.views.PlayerProfile.PlayerProfileFragment;
import com.example.ipingpong.views.PlayerProfile.ReceiveNotificationFragment;
import com.example.ipingpong.views.PlayerProfile.SessionTrainingFragment;
import com.example.ipingpong.views.Reports.MainReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClubManagerActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_manager);

        bottomNavigationView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_players, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_Profile, R.id.navigation_dataset)
                .build();

        user = (User) getIntent().getSerializableExtra("User");

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        CoachesListFragment coachesListFragment = new CoachesListFragment();
        replaceFragment(coachesListFragment, "Coaches List", false);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.navigation_coaches:
                CoachesListFragment coachesListFragment = new CoachesListFragment();
                replaceFragment(coachesListFragment, "Coaches List", false);
                break;

            case R.id.navigation_club_manager_profile:
                ClubManagerProfileFragment clubManagerProfileFragment = new ClubManagerProfileFragment(this, Action.ViewUpdate, user);
                replaceFragment(clubManagerProfileFragment, "Club Manager Profile", false);
                break;
        }

        return true;

    }
}
