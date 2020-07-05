package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.User;

import java.util.ArrayList;

public interface SelectUsersByUserTypesCallBack extends errorCallBack {
    void onSuccess(ArrayList<User> users);
}
