package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.shared.entities.UserType;

public interface LoginCallBack extends errorCallBack {
    void onLoginSuccess(User user);
}
