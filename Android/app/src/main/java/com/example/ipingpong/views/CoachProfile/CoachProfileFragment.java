package com.example.ipingpong.views.CoachProfile;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.Base.ProfileBaseFragments;

public class CoachProfileFragment extends ProfileBaseFragments {

    public CoachProfileFragment(Context context, Action action, User user) {
        this.action = action;
        this.context = context;
        this.userTypeID = 2;
        this.user = user;

        System.out.println("User First Name:: " + user.getFirstName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coach_profile, container, false);

        setBasicSettings(view);

        if(action == Action.ViewUpdate || action == action.View ){
            displayProfileData();

            if(action == Action.View){
                profileActionBtn.setVisibility(View.GONE);
                imageEditBtn.setVisibility(View.GONE);
            }

        }else{

            imageEditBtn.setVisibility(View.GONE);
            profileActionBtn.setText("Add Coach");
            enableAllViews();
        }




        return view;
    }

    @Override
    protected void initViews(View view) {}

    @Override
    protected void setListeners() {}

    @Override
    protected void enableViews() {}

    @Override
    protected void disableViews() {}

    @Override
    protected void validateChange() {}

    @Override
    public void displayProfileData() {
        displayUserProfileData();
    }

    @Override
    public void updateProfileData() {}

    @Override
    public void addProfileData(int userID) {}

    @Override
    public boolean validateAddFields() {
        return true;
    }
}
