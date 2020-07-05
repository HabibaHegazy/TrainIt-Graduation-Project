package com.example.ipingpong.models;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.ReceiveNotificationCallBack;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.datasource.remote.RequestApi;
import com.example.ipingpong.shared.datasource.remote.VolleyCallBack;
import com.example.ipingpong.shared.entities.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationModel {

    private RequestApi requestApi;

    public NotificationModel(Context context) {
        this.requestApi = new RequestApi(context);
    }

    public void receiveNotification(final HashMap<String, String> conditions, final ReceiveNotificationCallBack receiveNotificationCallBack)
    {
        requestApi.selectApi(new VolleyCallBack() {

            @Override
            public void onSuccess(JSONObject response) {

                //System.out.println(result);

                try {

                    final ArrayList<Notification> notifications = new ArrayList<>();

                    if (response.getBoolean("error")) {
                        receiveNotificationCallBack.onFailure(response.getString("message"));
                    } else {

                        JSONArray jsonArray = response.getJSONArray(Constants.table_name_notification);
                        int x = 0;
                        while (x < jsonArray.length()) {

                            JSONObject jsonObject = (JSONObject) jsonArray.get(x);

                            final Notification notification = new Notification();
                            notification.setSubject(jsonObject.getString("subject"));
                            notification.setMessage(jsonObject.getString("message"));
                            notification.setIsRead(jsonObject.getInt("isRead"));
                            notifications.add(notification);

                            x++;
                        }

                        receiveNotificationCallBack.onSuccess(notifications);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(String reason) {
                receiveNotificationCallBack.onFailure(reason);
            }

        }, Constants.table_name_notification, conditions);


    }
}


