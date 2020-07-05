package com.example.ipingpong.views.Base;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChartView;
import com.example.ipingpong.controllers.CallBacks.GenerateReportCallBack;
import com.example.ipingpong.controllers.PlayerController;
import com.example.ipingpong.shared.entities.Player;

import java.util.HashMap;

public abstract class BaseReport extends Fragment {

    protected AnyChartView anyChartView;
    protected ProgressBar progressBar;
    public Player player;
    protected String report = "";
    protected PlayerController playerController = new PlayerController(getContext());

    public void createReport(String report, Player player) {

        playerController.generateReport(player.getId(), report, new GenerateReportCallBack() {
            @Override
            public void onSuccess(HashMap<String, String> data) {

                if(data.size() == 0){
                    Toast.makeText(getActivity(), "No data is available", Toast.LENGTH_LONG).show();
                }else {
                    CreateGraph(data);
                }
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    protected abstract void CreateGraph(HashMap<String, String> map);



}
