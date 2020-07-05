package com.example.ipingpong.models;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.SelectAllAddresses;
import com.example.ipingpong.controllers.CallBacks.SelectSingleAddress;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.datasource.remote.RequestApi;
import com.example.ipingpong.shared.datasource.remote.VolleyCallBack;
import com.example.ipingpong.shared.entities.Address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressModel {

    private RequestApi requestApi;

    public AddressModel(Context context) {
        this.requestApi = new RequestApi(context);
    }

    public void getAddresses(int parentID, final SelectAllAddresses geAllAddresses) {

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("parentID", String.valueOf(parentID));

        requestApi.selectApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                //System.out.println(result);

                try {
                    final ArrayList<Address> addresses = new ArrayList<>();

                    if (response.getBoolean("error")) {
                        geAllAddresses.onFailure(response.getString("message"));
                    }
                    else{

                        JSONArray jsonArray = response.getJSONArray(Constants.table_name_address);

                        int x = 0;
                        while (x < jsonArray.length()){

                            JSONObject jsonObject = (JSONObject) jsonArray.get(x);

                            final Address address = new Address();
                            address.setId(jsonObject.getInt("id"));
                            address.setAddressName(jsonObject.getString("addressName"));
                            address.setParentID(jsonObject.getInt("parentID"));
                            addresses.add(address);

                            x++;
                        }

                        geAllAddresses.onSuccess(addresses);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String reason) {
                geAllAddresses.onFailure(reason);
            }
        }, Constants.table_name_address, conditions);
    }

    public void getSingleAddress(String addressID, final SelectSingleAddress selectSingleAddress){

        HashMap<String, String> conditions = new HashMap<>();
        conditions.put("id", addressID);

            requestApi.selectApi(new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject response) {

                    //System.out.println(result);

                    try {

                        if (response.getBoolean("error")) {
                            selectSingleAddress.onFailure(response.getString("message"));
                        }
                        else{

                            JSONObject jsonObject = (JSONObject) response.getJSONArray(Constants.table_name_address).get(0);

                            Address address = new Address();
                            address.setId(jsonObject.getInt("id"));
                            address.setAddressName(jsonObject.getString("addressName"));
                            address.setParentID(jsonObject.getInt("parentID"));

                            selectSingleAddress.onSuccess(address);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(String reason) {
                    selectSingleAddress.onFailure(reason);
                }
            }, Constants.table_name_address, conditions);
    }
}
