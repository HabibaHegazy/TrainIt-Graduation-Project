package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.Club;
import com.example.ipingpong.shared.entities.User;

public interface SelectClubCallBack extends errorCallBack {
    void onSuccess(Club club);
}
