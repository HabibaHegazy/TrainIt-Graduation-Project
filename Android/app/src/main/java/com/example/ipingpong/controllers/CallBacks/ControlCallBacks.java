package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;

public interface ControlCallBacks extends errorCallBack { // for insert, delete, update of any table

    void onSuccess(String success);

}
