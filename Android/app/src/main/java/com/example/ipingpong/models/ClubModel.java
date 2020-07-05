package com.example.ipingpong.models;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.InsertUserCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectSingleAddress;
import com.example.ipingpong.controllers.CallBacks.SelectClubCallBack;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.datasource.remote.RequestApi;
import com.example.ipingpong.shared.datasource.remote.VolleyCallBack;
import com.example.ipingpong.shared.entities.Address;
import com.example.ipingpong.shared.entities.Club;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ClubModel {

    private RequestApi requestApi;
    private AddressModel addressModel;

    public ClubModel(Context context) {
        this.requestApi = new RequestApi(context);
        this.addressModel = new AddressModel(context);
    }

    public void getClub(String clubID, final SelectClubCallBack selectClubCallBack){

        HashMap<String, String> conditions = new HashMap<>();
        conditions.put("id", clubID);

            requestApi.selectApi(new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject response) {

                    //System.out.println(result);

                    try {

                        if (response.getBoolean("error")) {
                            selectClubCallBack.onFailure(response.getString("message"));
                        }
                        else{

                            JSONArray jsonArray = response.getJSONArray(Constants.table_name_club);
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                            final Club club = new Club();
                            club.setId(jsonObject.getInt("id"));
                            club.setRate(jsonObject.getInt("clubRate"));
                            club.setName(jsonObject.getString("clubName"));

                            System.out.println(club.getRate());

                            addressModel.getSingleAddress(String.valueOf(jsonObject.getInt("addressID")), new SelectSingleAddress() {

                                @Override
                                public void onSuccess(Address address) {
                                    club.setAddress(address);
                                    selectClubCallBack.onSuccess(club);
                                }

                                @Override
                                public void onFailure(String reason) {
                                    selectClubCallBack.onFailure(reason);
                                }
                            });

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(String reason) {
                    selectClubCallBack.onFailure(reason);
                }
            }, Constants.table_name_club, conditions);
    }

    public void insertClub(HashMap<String, String> data, final ControlCallBacks insertClubCallBack){

        requestApi.insertApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    if(response.getBoolean("error")){
                        insertClubCallBack.onFailure(response.getString("message"));
                    }
                    else {
                        insertClubCallBack.onSuccess(response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String reason) {
                insertClubCallBack.onFailure(reason);
            }
        }, Constants.table_name_users, data);

    }

}
