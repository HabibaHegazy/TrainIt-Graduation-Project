package com.example.ipingpong.controllers;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.models.ClubModel;

import java.util.HashMap;

public class ClubController {

    ClubModel clubModel;

    public ClubController(Context context) {
        this.clubModel = new ClubModel(context);
    }

    public void addClub(HashMap<String, String> conditions, final ControlCallBacks controlCallBacks){

        clubModel.insertClub(conditions, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                controlCallBacks.onSuccess(success);
            }

            @Override
            public void onFailure(String reason) {
                controlCallBacks.onFailure(reason);
            }
        });

    }

    public void updateClub(){

    }

    public void deleteClub(){
        // when delete a club you needed every member on that club
    }


}
