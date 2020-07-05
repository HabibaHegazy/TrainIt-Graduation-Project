package com.example.ipingpong.models;

import android.content.Context;
import com.example.ipingpong.controllers.CallBacks.ControlCallBacks;
import com.example.ipingpong.controllers.CallBacks.InsertUserCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectSingleAddress;
import com.example.ipingpong.controllers.CallBacks.LoginCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectClubCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectUserCallBack;
import com.example.ipingpong.controllers.CallBacks.SelectUsersByUserTypesCallBack;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.datasource.remote.RequestApi;
import com.example.ipingpong.shared.datasource.remote.VolleyCallBack;
import com.example.ipingpong.shared.entities.Address;
import com.example.ipingpong.shared.entities.Club;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.shared.entities.UserType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {
    private RequestApi requestApi;
    private ClubModel clubModel;
    private AddressModel addressModel;

    public UserModel(Context context) {
        this.requestApi = new RequestApi(context);
        this.clubModel = new ClubModel(context);
        this.addressModel = new AddressModel(context);
    }

    public void login(HashMap<String, String> conditions, final LoginCallBack loginCallBack) {

        requestApi.selectApi(new VolleyCallBack() {

            @Override
            public void onSuccess(JSONObject response) {

                try {

                    if (response.getBoolean("error")) {
                        loginCallBack.onFailure(response.getString("message"));
                    } else {

                        final JSONObject userJson = (JSONObject) response.getJSONArray(Constants.table_name_users).get(0);


                        final User user = new User();
                        user.setId(userJson.getInt("id"));
                        user.setFirstName(userJson.getString("firstName"));
                        user.setLastName(userJson.getString("lastName"));
                        user.setEmail(userJson.getString("email"));
                        user.setPassword(userJson.getString("password"));
                        user.setGender(userJson.getString("gender"));
                        user.setPhoneNumber(userJson.getString("phoneNumber"));
                        user.setBirthdate(userJson.getString("birthdate"));

                        UserType userType;

                        int userTypeId = userJson.getInt("usertypeID");

                        if (userTypeId == UserType.COACH.getValue()) {
                            user.setUserType(UserType.COACH);
                        } else if(userTypeId == UserType.PLAYER.getValue()){
                            user.setUserType(UserType.PLAYER);
                        } else if(userTypeId == UserType.ClubManager.getValue()){
                            user.setUserType(UserType.ClubManager);
                        } else{
                            user.setUserType(UserType.Admin);
                        }

                        clubModel.getClub(String.valueOf(userJson.getInt("clubID")), new SelectClubCallBack() {
                            @Override
                            public void onSuccess(Club club) {
                                user.setClub(club);

                                try {
                                    addressModel.getSingleAddress(String.valueOf(userJson.getInt("addressID")), new SelectSingleAddress() {
                                        @Override
                                        public void onSuccess(Address address) {
                                            user.setAddress(address);
                                        }

                                        @Override
                                        public void onFailure(String reason) {
                                            loginCallBack.onFailure(reason);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(String reason) {
                                loginCallBack.onFailure(reason);
                            }
                        });

                        loginCallBack.onLoginSuccess(user);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String reason) {
                loginCallBack.onFailure(reason);
            }
        }, Constants.table_name_users, conditions);
    }

    public void getUser(int userID, final SelectUserCallBack selectUserCallBack){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("id", String.valueOf(userID));

        requestApi.selectApi(new VolleyCallBack() {

            @Override
            public void onSuccess(JSONObject response) {

                try {

                    if (response.getBoolean("error")) {
                        selectUserCallBack.onFailure(response.getString("message"));
                    }
                    else {

                        final JSONObject userJson = (JSONObject) response.getJSONArray(Constants.table_name_users).get(0);

                        final User user = new User();
                        user.setId(userJson.getInt("id"));
                        user.setFirstName(userJson.getString("firstName"));
                        user.setLastName(userJson.getString("lastName"));
                        user.setEmail(userJson.getString("email"));
                        user.setPassword(userJson.getString("password"));
                        user.setGender(userJson.getString("gender"));
                        user.setPhoneNumber(userJson.getString("phoneNumber"));
                        user.setBirthdate(userJson.getString("birthdate"));

                        int userTypeId = userJson.getInt("usertypeID");

                        if (userTypeId == UserType.COACH.getValue()) {
                            user.setUserType(UserType.COACH);
                        } else if(userTypeId == UserType.PLAYER.getValue()){
                            user.setUserType(UserType.PLAYER);
                        } else if(userTypeId == UserType.ClubManager.getValue()){
                            user.setUserType(UserType.ClubManager);
                        } else{
                            user.setUserType(UserType.Admin);
                        }

                        clubModel.getClub(String.valueOf(userJson.getInt("clubID")), new SelectClubCallBack() {
                            @Override
                            public void onSuccess(Club club) {
                                user.setClub(club);

                                try {
                                    addressModel.getSingleAddress(String.valueOf(userJson.getInt("addressID")), new SelectSingleAddress() {
                                        @Override
                                        public void onSuccess(Address address) {
                                            user.setAddress(address);
                                        }

                                        @Override
                                        public void onFailure(String reason) {
                                            selectUserCallBack.onFailure(reason);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(String reason) {
                                selectUserCallBack.onFailure(reason);
                            }
                        });

                        selectUserCallBack.onSuccess(user);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String reason) {
                selectUserCallBack.onFailure(reason);
            }
        }, Constants.table_name_users, conditions);



    }

    public void deleteUser(int userID, final ControlCallBacks deleteUserCallBacks){

        HashMap<String, String> conditions = new HashMap<String, String>();
        conditions.put("id", String.valueOf(userID));

        requestApi.deleteApi(new VolleyCallBack() {

            @Override
            public void onSuccess(JSONObject response) {

                try {

                    //System.out.println(result);

                    if (response.getBoolean("error")) {
                        deleteUserCallBacks.onFailure(response.getString("message"));
                    }
                    else {

                        deleteUserCallBacks.onSuccess(response.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String reason) {
                deleteUserCallBacks.onFailure(reason);
            }
        }, Constants.table_name_users, conditions);
    }

    public void getUsers(HashMap<String, String> conditions, final SelectUsersByUserTypesCallBack selectUsersByUserTypesCallBack){

        requestApi.selectApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                //System.out.println(result);

                try {
                    final ArrayList<User> users = new ArrayList<>();

                    if (response.getBoolean("error")) {
                        selectUsersByUserTypesCallBack.onFailure(response.getString("message"));
                    }
                    else{

                        JSONArray jsonArray = response.getJSONArray(Constants.table_name_users);
                        int x = 0;
                        while (x < jsonArray.length()){

                            final JSONObject jsonObject = (JSONObject) jsonArray.get(x);

                            System.out.println(jsonArray.length());

                            final User user = new User();
                            user.setId(jsonObject.getInt("id"));
                            user.setFirstName(jsonObject.getString("firstName"));
                            user.setLastName(jsonObject.getString("lastName"));
                            user.setEmail(jsonObject.getString("email"));
                            user.setPassword(jsonObject.getString("password"));
                            user.setGender(jsonObject.getString("gender"));
                            user.setPhoneNumber(jsonObject.getString("phoneNumber"));
                            user.setBirthdate(jsonObject.getString("birthdate"));

                            int userTypeId = jsonObject.getInt("usertypeID");

                            if (userTypeId == UserType.COACH.getValue()) {
                                user.setUserType(UserType.COACH);
                            } else if(userTypeId == UserType.PLAYER.getValue()){
                                user.setUserType(UserType.PLAYER);
                            } else if(userTypeId == UserType.ClubManager.getValue()){
                                user.setUserType(UserType.ClubManager);
                            } else{
                                user.setUserType(UserType.Admin);
                            }

                            clubModel.getClub(String.valueOf(jsonObject.getInt("clubID")), new SelectClubCallBack() {
                                @Override
                                public void onSuccess(Club club) {
                                    user.setClub(club);

                                    try {
                                        addressModel.getSingleAddress(String.valueOf(jsonObject.getInt("addressID")), new SelectSingleAddress() {
                                            @Override
                                            public void onSuccess(Address address) {
                                                user.setAddress(address);
                                                users.add(user);
                                                selectUsersByUserTypesCallBack.onSuccess(users);
                                            }

                                            @Override
                                            public void onFailure(String reason) {
                                                selectUsersByUserTypesCallBack.onFailure(reason);
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(String reason) {
                                    selectUsersByUserTypesCallBack.onFailure(reason);
                                }
                            });

                            x++;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String reason) {
                selectUsersByUserTypesCallBack.onFailure(reason);
            }
        }, Constants.table_name_users, conditions);

    }

    public void insertUser(HashMap<String, String> data, final InsertUserCallBack insertUserCallBack){

        requestApi.insertApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    if(response.getBoolean("error")){
                        insertUserCallBack.onFailure(response.getString("message"));
                    }
                    else {
                        insertUserCallBack.onSuccess(response.getString("message"), response.getInt("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String reason) {
                insertUserCallBack.onFailure(reason);
            }
        }, Constants.table_name_users, data);

    }

    public void updateUser(HashMap<String, String> data, HashMap<String, String> conditions, final ControlCallBacks updateUserCallBack){

        requestApi.updateApi(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    if(response.getBoolean("error")){
                        updateUserCallBack.onFailure(response.getString("message"));
                    }else{
                        updateUserCallBack.onSuccess(response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String reason) {
                updateUserCallBack.onFailure(reason);
            }
        },Constants.table_name_users,data,conditions);

    }

}
