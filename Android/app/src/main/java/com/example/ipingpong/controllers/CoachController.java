package com.example.ipingpong.controllers;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.GenerateReportCallBack;
import com.example.ipingpong.controllers.CallBacks.MonitoringCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectPlayersCallBack;
import com.example.ipingpong.models.PlayerModel;
import com.example.ipingpong.models.UserModel;
import com.example.ipingpong.shared.entities.ClassificationResult;
import com.example.ipingpong.shared.entities.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CoachController {

    private UserModel userModel;
    private PlayerModel playerModel;

    public CoachController(Context context) {
        this.userModel = new UserModel(context);
        this.playerModel = new PlayerModel(context);
    }

    public void getPlayersList(int currentCoachId, final SelectPlayersCallBack selectPlayersCallBack) {

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("coachedBy", String.valueOf(currentCoachId));

        playerModel.getPlayers(conditions, new SelectPlayersCallBack() {
            @Override
            public void onSuccess(ArrayList<Player> players) {
                selectPlayersCallBack.onSuccess(players);
            }

            @Override
            public void onFailure(String reason) {
                selectPlayersCallBack.onFailure(reason);
            }
        });
    }

    public void deletePlayer(final int playerID, final ControlCallBacks deleteUserCallBacks){


        userModel.deleteUser(playerID, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                playerModel.deletePlayer(playerID, new ControlCallBacks() {
                    @Override
                    public void onSuccess(String success) {
                        deleteUserCallBacks.onSuccess(success);
                    }

                    @Override
                    public void onFailure(String reason) {
                        deleteUserCallBacks.onFailure(reason);
                    }
                });
            }

            @Override
            public void onFailure(String reason) {
                deleteUserCallBacks.onFailure(reason);
            }
        });
    }

    public void generateComparisonReport(final String reportOn, ArrayList<Player> playerArrayList, final GenerateReportCallBack generateReportCallBack){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("choice", reportOn);

        for(int i=0; i< playerArrayList.size(); i++){
            conditions.put("playerID"+i, String.valueOf(playerArrayList.get(i).getId()));
        }

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

    public void getStrokesOfPlayer(int playerID, String date, final MonitoringCallBack monitoringCallBack){
        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("date", date);
        conditions.put("playerID", String.valueOf(playerID));
        conditions.put("readed" , "0");

        playerModel.getPlayerClassificationResult(conditions, new MonitoringCallBack() {
            @Override
            public void onSuccess(ClassificationResult classificationResult) {
                monitoringCallBack.onSuccess(classificationResult);
            }

            @Override
            public void onFailure(String reason) {
                monitoringCallBack.onFailure(reason);
            }
        });


    }
}
