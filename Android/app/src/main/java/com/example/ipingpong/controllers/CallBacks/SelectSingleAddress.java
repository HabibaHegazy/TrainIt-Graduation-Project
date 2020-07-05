package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.Address;

import java.util.ArrayList;

public interface SelectSingleAddress extends errorCallBack {
    void onSuccess(Address address);
}
