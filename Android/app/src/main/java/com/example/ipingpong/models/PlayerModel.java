package com.example.ipingpong.models;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.GenerateReportCallBack;
import com.example.ipingpong.controllers.CallBacks.InsertUserCallBack;
import com.example.ipingpong.controllers.CallBacks.MonitoringCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectPlayerCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectPlayersCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectUserCallBack;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.datasource.remote.RequestApi;
import com.example.ipingpong.shared.datasource.remote.VolleyCallBack;
import com.example.ipingpong.shared.entities.ClassificationResult;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.shared.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerModel {
    private RequestApi requestApi;
    private UserModel userModel;

    public PlayerModel(Context context) {
        this.requestApi = new RequestApi(context);
        this.userModel = new UserModel(context);
    }

    public void getPlayers(HashMap<String, String> conditions, final SelectPlayersCallBack selectPlayersCallBack){

        requestApi.selectApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    final ArrayList<Player> players = new ArrayList<>();

                    if (response.getBoolean("error")) {
                        selectPlayersCallBack.onFailure(response.getString("message"));
                    }
                    else{

                        JSONArray jsonArray = response.getJSONArray(Constants.table_name_players);
                        int x = 0;
                        while (x < jsonArray.length()){

                            JSONObject jsonObject = (JSONObject) jsonArray.get(x);

                            final Player player = new Player();
                            player.setId(jsonObject.getInt("id"));
                            player.setHandUsed(jsonObject.getString("handUsed"));
                            player.setPlayerNumber(jsonObject.getInt("playerNumber"));
                            player.setLevel(jsonObject.getInt("level"));

                            userModel.getUser(jsonObject.getInt("userID"), new SelectUserCallBack() {
                                @Override
                                public void onSuccess(User user) {
                                    player.setUser(user);
                                    players.add(player);
                                    selectPlayersCallBack.onSuccess(players);
                                }

                                @Override
                                public void onFailure(String reason) {
                                    selectPlayersCallBack.onFailure(reason);
                                }
                            });

                            x++;
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
                public void onFailure(String reason) {
                selectPlayersCallBack.onFailure(reason);
            }
        }, Constants.table_name_players, conditions);

    }

    public void generateReport(HashMap<String, String> conditions, final GenerateReportCallBack generateReportCallBack){

        requestApi.reportApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {

                    if (response.getBoolean("error")) {
                        generateReportCallBack.onFailure(response.getString("message"));
                    }
                    else{

                        JSONArray jsonArray = response.getJSONArray(Constants.table_name_reports);
                        HashMap<String, String> map = new HashMap<>();

                        int x = 0;
                        while (x < jsonArray.length()){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(x);

                            map.put(jsonObject.getString("report"), jsonObject.getString("performanceRate"));
                            x++;
                        }

                        generateReportCallBack.onSuccess(map);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String reason) {
                generateReportCallBack.onFailure(reason);
            }
        }, Constants.table_name_reports, conditions);

    }

    public void getPlayerByUserID(int userID, final SelectPlayerCallBack selectPlayerCallBack){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("userID", String.valueOf(userID));

        requestApi.selectApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                //System.out.println(result);

                try {

                    if (response.getBoolean("error")) {
                        selectPlayerCallBack.onFailure(response.getString("message"));
                    }
                    else{

                        JSONArray jsonArray = response.getJSONArray(Constants.table_name_players);
                        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                            Player player = new Player();
                            player.setId(jsonObject.getInt("id"));
                            player.setHandUsed(jsonObject.getString("handUsed"));
                            player.setPlayerNumber(jsonObject.getInt("playerNumber"));
                            player.setLevel(jsonObject.getInt("level"));

                            selectPlayerCallBack.onSuccess(player);

                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String reason) {
                selectPlayerCallBack.onFailure(reason);
            }
        }, Constants.table_name_players, conditions);


    }

    public void deletePlayer(int playerID, final ControlCallBacks deleteUserCallBacks){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("id", String.valueOf(playerID));

        requestApi.deleteApi(new VolleyCallBack() {

            @Override
            public void onSuccess(JSONObject response) {

                try {

                    //System.out.println(result);

                    if (response.getBoolean("error")) {
                        deleteUserCallBacks.onFailure(response.getString("message"));
                    }
                    else {
                        deleteUserCallBacks.onSuccess(response.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(String reason) {
                deleteUserCallBacks.onFailure(reason);
            }
        }, Constants.table_name_players, conditions);
    }

    public void insertPlayer(HashMap<String, String> data, final ControlCallBacks insertCallBack){

        requestApi.insertApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    if(response.getBoolean("error")){
                        insertCallBack.onFailure(response.getString("message"));
                    }
                    else {
                        insertCallBack.onSuccess(response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String reason) {
                insertCallBack.onFailure(reason);
            }
        }, Constants.table_name_players, data);

    }

    public void updatePlayer(HashMap<String, String> data, HashMap<String, String> conditions, final ControlCallBacks updateUserCallBack){

        requestApi.updateApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    if(response.getBoolean("error")){
                        updateUserCallBack.onFailure(response.getString("message"));
                    }else{
                        updateUserCallBack.onSuccess(response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String reason) {
                updateUserCallBack.onFailure(reason);
            }
        },Constants.table_name_players,data,conditions);

    }

    public void getPlayerClassificationResult(final HashMap<String, String> conditions, final MonitoringCallBack monitoringCallBack){


        requestApi.selectApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {

                    if (response.getBoolean("error")) {
                        monitoringCallBack.onFailure(response.getString("message"));
                    }
                    else {

                        JSONArray jsonArray = response.getJSONArray(Constants.table_name_classification_result);

                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                            final ClassificationResult cr = new ClassificationResult();
                            cr.setErrorType(jsonObject.getString("errorType"));

                            if (jsonObject.getInt("isMistake") == 0) {
                                cr.setMistake(false);
                            } else {
                                cr.setMistake(true);
                            }
                            cr.setStrokeType(jsonObject.getString("strokeType"));

                            // update readed = 1
                            HashMap<String, String> data = new HashMap<>();
                            data.put("readed", "1");
                            requestApi.updateApi(new VolleyCallBack() {
                                @Override
                                public void onSuccess(JSONObject response) {
                                    monitoringCallBack.onSuccess(cr);
                                }

                                @Override
                                public void onFailure(String reason) {
                                    monitoringCallBack.onFailure(reason);
                                }
                            }, Constants.table_name_classification_result, data, conditions);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String reason) {
                monitoringCallBack.onFailure(reason);
            }
        }, Constants.table_name_classification_result, conditions);


    }

}
