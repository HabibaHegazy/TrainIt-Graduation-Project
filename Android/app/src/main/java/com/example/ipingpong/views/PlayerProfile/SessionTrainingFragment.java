package com.example.ipingpong.views.PlayerProfile;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ipingpong.R;
import com.example.ipingpong.views.Base.BaseSignalIntake;

public class SessionTrainingFragment extends BaseSignalIntake {

    public SessionTrainingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_session_training, container, false);

        intialization(view);
        return view;
    }

    @Override
    public void clickEnd() {
        PlayerActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        startBtn.setEnabled(true);
        startBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        Stop();
    }

//    public String playedStrokeAR(){
//        return "Backhand";
//    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

