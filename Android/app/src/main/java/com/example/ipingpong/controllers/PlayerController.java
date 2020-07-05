package com.example.ipingpong.controllers;

import android.content.Context;

import com.anychart.chart.common.dataentry.DataEntry;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.GenerateReportCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectPlayerCallBack;
import com.example.ipingpong.models.PlayerModel;
import com.example.ipingpong.models.UserModel;
import com.example.ipingpong.shared.entities.Days;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerController {

    private PlayerModel playerModel;

    public PlayerController(Context context) {
        this.playerModel = new PlayerModel(context);
    }

    public void generateReport(int playerID, final String reportOn, final GenerateReportCallBack generateReportCallBack){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("choice", reportOn);
        conditions.put("playerID", String.valueOf(playerID));

        playerModel.generateReport(conditions, new GenerateReportCallBack() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                generateReportCallBack.onSuccess(data);
            }

            @Override
            public void onFailure(String reason) {
                generateReportCallBack.onFailure(reason);
            }
        });
    }

    public void getPlayerByUserID(final User user, final SelectPlayerCallBack selectPlayerCallBack){

        playerModel.getPlayerByUserID(user.getId(), new SelectPlayerCallBack() {
            @Override
            public void onSuccess(Player player) {
                player.setUser(user);
                selectPlayerCallBack.onSuccess(player);
            }

            @Override
            public void onFailure(String reason) {
                selectPlayerCallBack.onFailure(reason);
            }
        });

    }

    public void addPlayer(HashMap<String, String> data, int userID, final ControlCallBacks insertCallBack){

        data.put("userID", String.valueOf(userID));
        data.put("coachedBy", String.valueOf(1)); // the coach id from shared perefrence

        playerModel.insertPlayer(data, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                insertCallBack.onSuccess(success);
            }

            @Override
            public void onFailure(String reason) {
                insertCallBack.onFailure(reason);
            }
        });

    }

    public void updatePlayer(HashMap<String, String> data, int playerID, final ControlCallBacks updateCallBack){

        HashMap<String, String> conditions = new HashMap<>();
        conditions.put("id", String.valueOf(playerID));

        playerModel.updatePlayer(data, conditions, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                updateCallBack.onSuccess(success);
            }

            @Override
            public void onFailure(String reason) {
                updateCallBack.onFailure(reason);
            }
        });

    }

}
