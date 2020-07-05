package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.ClassificationResult;

import java.util.ArrayList;

public interface MonitoringCallBack extends errorCallBack {
    void onSuccess(ClassificationResult classificationResult);

}
