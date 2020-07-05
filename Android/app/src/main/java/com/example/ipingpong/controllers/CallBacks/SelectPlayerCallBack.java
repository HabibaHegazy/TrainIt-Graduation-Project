package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.Player;

public interface SelectPlayerCallBack extends errorCallBack {
    void onSuccess(Player player);
}
