package com.example.ipingpong.controllers.CallBacks;

import com.example.ipingpong.shared.datasource.remote.errorCallBack;
import com.example.ipingpong.shared.entities.Player;
import java.util.ArrayList;

public interface SelectPlayersCallBack extends errorCallBack {
    void onSuccess(ArrayList<Player> players);
}
