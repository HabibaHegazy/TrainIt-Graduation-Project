package com.example.ipingpong.views.CoachProfile;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.DialogEntities;
import com.example.ipingpong.util.CustomDialog;
import com.example.ipingpong.views.Base.BaseSignalIntake;
import com.example.ipingpong.views.PlayerProfile.PlayerActivity;


public class DatasetEntryFragment extends BaseSignalIntake {

    public DatasetEntryFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dataset_entry, container, false);

        intialization(view);

        return view;
    }

    @Override
    public void clickEnd() {
        CustomDialog customDialog = new CustomDialog(DialogEntities.DatasetData);
        customDialog.show(getActivity().getSupportFragmentManager(), "Dataset Information");

        PlayerActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        startBtn.setEnabled(true);
        startBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
