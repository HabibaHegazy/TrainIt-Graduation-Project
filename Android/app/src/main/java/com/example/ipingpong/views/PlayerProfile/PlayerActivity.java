package com.example.ipingpong.views.PlayerProfile;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.SelectPlayerCallBack;
import com.example.ipingpong.controllers.PlayerController;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.Base.BaseActivity;
import com.example.ipingpong.views.Reports.MainReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlayerActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private PlayerController playerController;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerController = new PlayerController(this);

        bottomNavigationView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_player_profile, R.id.navigation_player_performance, R.id.navigation_notifications_list, R.id.navigation_training)
                .build();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        getPlayerData((User) getIntent().getSerializableExtra("User"));


        SessionTrainingFragment sessionTrainingFragment = new SessionTrainingFragment();
        replaceFragment(sessionTrainingFragment, "Training Session", false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.navigation_training:
                SessionTrainingFragment sessionTrainingFragment = new SessionTrainingFragment();
                replaceFragment(sessionTrainingFragment, "Training Session", false);
                break;

            case R.id.navigation_player_profile:
                PlayerProfileFragment playerProfileFragment = new PlayerProfileFragment(this, Action.View, player);
                replaceFragment(playerProfileFragment, "Player Profile", false);
                break;

            case R.id.navigation_player_performance:
                MainReportFragment mainReportFragment = new MainReportFragment(player);
                replaceFragment(mainReportFragment, "Player Performance", false);
                break;

            case R.id.navigation_notifications_list:
                ReceiveNotificationFragment receiveNotificationFragment = new ReceiveNotificationFragment(player.getUser());
                replaceFragment(receiveNotificationFragment, "Player Performance", false);
                break;
        }

        return true;
    }

    private void getPlayerData(User user){

        playerController.getPlayerByUserID(user, new SelectPlayerCallBack() {
            @Override
            public void onSuccess(Player player) {
                setPlayer(player);
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(PlayerActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

