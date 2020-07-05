package com.example.ipingpong.controllers;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.ReceiveNotificationCallBack;
import com.example.ipingpong.models.NotificationModel;
import com.example.ipingpong.shared.entities.Notification;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationController {

    private NotificationModel notificationModel;

    public NotificationController(Context context) {
        this.notificationModel = new NotificationModel(context);
    }

    public void receiveMessage(String playerID, final ReceiveNotificationCallBack receiveNotificationCallBack){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("toUserID", playerID);

        notificationModel.receiveNotification(conditions, new ReceiveNotificationCallBack() {
            @Override
            public void onSuccess(ArrayList<Notification> notifications) {
                receiveNotificationCallBack.onSuccess(notifications);
            }

            @Override
            public void onFailure(String reason) {
                receiveNotificationCallBack.onFailure(reason);
            }
        });

    }

    public void sendMessage(ArrayList<Integer> ids, final ControlCallBacks sendNotification){


    }

}
