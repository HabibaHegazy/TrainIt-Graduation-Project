package com.example.ipingpong.shared.datasource.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ipingpong.shared.entities.User;
import com.google.gson.Gson;

public class SharedPrefManager {

    private Context ctx;
    //private static SharedPrefManager mInstance;
    public static final String PREFS_NAME = "LoginPrefs";


    public SharedPrefManager(Context context) {
        ctx = context;
    }

//    public static synchronized SharedPrefManager getInstance(Context context) {
//        if (mInstance == null) {
//            mInstance = new SharedPrefManager(context);
//        }
//        return mInstance;
//    }

    public void login(User user) {
        SharedPreferences mPrefs = ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("User", json);
        editor.putString("logged", "logged");
        editor.commit();
    }

    public User getUser() {
        SharedPreferences mPrefs = ctx.getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        String json = mPrefs.getString("User", "");
        User user = gson.fromJson(json, User.class);

        return user;

    }

    public void logout() {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("logged");
        editor.remove("User");
        editor.commit();
    }

    public boolean checkLogin(){
        SharedPreferences mPrefs = ctx.getSharedPreferences(PREFS_NAME, 0);
        return mPrefs.getString("logged", "").toString().equals("logged");
    }



}
