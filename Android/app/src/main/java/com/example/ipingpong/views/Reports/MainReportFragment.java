package com.example.ipingpong.views.Reports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.Player;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainReportFragment extends Fragment {

    private ReportDailyFragment reportDailyFragment;
    private ReportWeeklyFragment reportWeeklyFragment;
    private ReportMonthlyFragment reportMonthlyFragment;

    private Player player;

    public MainReportFragment(Player player) {
        this.player = player;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_report, container, false);

        reportDailyFragment = new ReportDailyFragment(player);
        reportMonthlyFragment = new ReportMonthlyFragment(player);
        reportWeeklyFragment = new ReportWeeklyFragment(player);


        BottomNavigationView appNav = view.findViewById(R.id.nav);

        appNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                switch (menuItem.getItemId()){

                    case R.id.item1:
                        ft.replace(R.id.report_fragment_container, reportDailyFragment);
                        ft.commit();
                        return true;

                    case R.id.item2:
                        ft.replace(R.id.report_fragment_container, reportWeeklyFragment);
                        ft.commit();
                        return true;

                    case R.id.item3:
                        ft.replace(R.id.report_fragment_container, reportMonthlyFragment);
                        ft.commit();
                        return true;

                    default:
                        return false;
                }

            }


        });

        return view;
    }


}
