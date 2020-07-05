package com.example.ipingpong.views.CoachProfile;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.MonitoringCallBack;
import com.example.ipingpong.controllers.CoachController;
import com.example.ipingpong.shared.datasource.local.SharedPrefManager;
import com.example.ipingpong.shared.entities.ClassificationResult;
import com.example.ipingpong.views.Adaptors.montieringAdaptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MonitorFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<ClassificationResult> strokeClassifiedList = new ArrayList<>();
    private CoachController coachController;
    private int id;
    String currentDate;
    int position = 1;

    public MonitorFragment(int id) {
        coachController = new CoachController(getActivity());
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_monitor, container, false);

        recyclerView = view.findViewById(R.id.recylerview_monitor);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(new Date());

        getStrokes();

        return view;
    }

    private void getStrokes() {

        final int nextPosition = 2000 * position;
        coachController.getStrokesOfPlayer(id, currentDate, new MonitoringCallBack() {

            @Override
            public void onSuccess(ClassificationResult classificationResult) {

                strokeClassifiedList.add(classificationResult);
                adapter = new montieringAdaptor(strokeClassifiedList, getActivity());
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(position - 1);
                position++;
                refresh(nextPosition);
            }

            @Override
            public void onFailure(String reason) {
                //Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
                refresh(nextPosition);
            }
        });
    }

    private void refresh(final int milliseconds){

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getStrokes();
            }
        }, milliseconds);
    }

}
