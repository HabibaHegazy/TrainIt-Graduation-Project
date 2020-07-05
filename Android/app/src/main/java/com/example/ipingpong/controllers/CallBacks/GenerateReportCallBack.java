package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;

import java.util.HashMap;

public interface GenerateReportCallBack extends errorCallBack {
    void onSuccess(HashMap<String, String> data);
}
