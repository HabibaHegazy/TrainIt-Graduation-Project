package com.example.ipingpong.shared.datasource.remote;

import org.json.JSONObject;

public interface VolleyCallBack extends errorCallBack {
    void onSuccess(JSONObject response);
}
