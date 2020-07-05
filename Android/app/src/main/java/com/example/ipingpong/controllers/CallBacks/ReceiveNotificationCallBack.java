package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.Notification;

import java.util.ArrayList;

public interface ReceiveNotificationCallBack extends errorCallBack {
    void onSuccess(ArrayList<Notification> notifications);
}
