package com.example.ipingpong.controllers;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.InsertUserCallBack;
import com.example.ipingpong.controllers.CallBacks.LoginCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectUsersByUserTypesCallBack;
import com.example.ipingpong.models.UserModel;
import com.example.ipingpong.shared.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UserController {

    private UserModel userModel;

    public UserController(Context context) {
        this.userModel = new UserModel(context);
    }

    public void getUsersByUserType(final String userTypeID, final SelectUsersByUserTypesCallBack selectUsersByUserTypesCallBack){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("usertypeID", userTypeID);
        conditions.put("isdeleted", String.valueOf(0));

        userModel.getUsers(conditions, new SelectUsersByUserTypesCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                selectUsersByUserTypesCallBack.onSuccess(users);
            }

            @Override
            public void onFailure(String reason) {
                selectUsersByUserTypesCallBack.onFailure(reason);
            }
        });

    }

    public void updateUser(HashMap<String, String> data, int userID, final ControlCallBacks updateCallBack){

        HashMap<String, String> conditions = new HashMap<>();
        conditions.put("id", String.valueOf(userID));

        userModel.updateUser(data, conditions, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                updateCallBack.onSuccess(success);
            }

            @Override
            public void onFailure(String reason) {
                updateCallBack.onFailure(reason);
            }
        });

    }

    public void addUser(HashMap<String, String> data, final InsertUserCallBack insertUserCallBack){

        data.put("password", randomPassword(data.get("lastName"))); // generate automatic later

        // check if user login -- then add the club ID from shared perefrence else nothing
        data.put("clubID", String.valueOf(1)); // the coach club id from shared perefrence

        userModel.insertUser(data, new InsertUserCallBack() {
            @Override
            public void onSuccess(String success, int userID) {
                insertUserCallBack.onSuccess(success, userID);
                // send email and SMS by the user email and password
            }

            @Override
            public void onFailure(String reason) {
                insertUserCallBack.onFailure(reason);
            }
        });

    }

    public void deleteUser(int userID, final ControlCallBacks deleteUserCallBacks){

        userModel.deleteUser(userID, new ControlCallBacks() {
            @Override
            public void onSuccess(String success) {
                deleteUserCallBacks.onSuccess(success);
            }

            @Override
            public void onFailure(String reason) {
                deleteUserCallBacks.onFailure(reason);
            }
        });

    }

    public void login(String email, String password, final LoginCallBack loginCallBack) {

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("email", email);
        conditions.put("password", password);
        conditions.put("isdeleted", String.valueOf(0));

        userModel.login(conditions, new LoginCallBack() {
            @Override
            public void onLoginSuccess(User user) {

                //storing the user in shared preferences
                //SharedPrefManager.getInstance(context).userLogin(user);
                loginCallBack.onLoginSuccess(user);
            }

            @Override
            public void onFailure(String reason) {

                loginCallBack.onFailure(reason);
            }
        });
    }

    private static String randomPassword(String lastname) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        randomStringBuilder.append(lastname);
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }


}
