package com.example.ipingpong.controllers.CallBacks;


import com.example.ipingpong.shared.datasource.remote.errorCallBack;

public interface InsertUserCallBack extends errorCallBack {
    void onSuccess(String success, int userID);

}
