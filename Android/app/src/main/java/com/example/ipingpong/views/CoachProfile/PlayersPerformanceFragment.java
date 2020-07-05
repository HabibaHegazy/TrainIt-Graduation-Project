package com.example.ipingpong.views.CoachProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.GenerateReportCallBack;
import com.example.ipingpong.controllers.CoachController;
import com.example.ipingpong.shared.datasource.Constants;
import com.example.ipingpong.shared.entities.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersPerformanceFragment extends Fragment {

    private AnyChartView anyChartView;
    private ProgressBar progressBar;
    private CoachController coachController;
    private ArrayList<Player> playerArrayList;
    private ArrayList<Integer> playerIDs = new ArrayList<>();

    List<DataEntry> data = new ArrayList<>();

    PlayersPerformanceFragment() {
        coachController = new CoachController(getContext());
        playerArrayList = Constants.playerArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players_performance, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        anyChartView = view.findViewById(R.id.any_chart_view2);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar2));


        for(int i = 0; i < playerArrayList.size(); i++){
            playerIDs.add(playerArrayList.get(i).getId());
            data.add(new ValueDataEntry(playerArrayList.get(i).getUser().getFirstName(), 0));
        }


        createReport();

        return view;
    }

    private void createReport() {

        coachController.generateComparisonReport("comaprisonYearly", playerArrayList, new GenerateReportCallBack() {
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

    private void CreateGraph(HashMap<String, String> map){

        Cartesian cartesian = AnyChart.column();
        cartesian.xScroller(true);

        for (Map.Entry<String, String> iterator : map.entrySet()) {
            int result = Math.round(Float.parseFloat(iterator.getValue()));

            for(int i = 0; i < playerArrayList.size(); i++){

                if(playerArrayList.get(i).getId() == Integer.parseInt(iterator.getKey())){
                    data.add(new ValueDataEntry(playerArrayList.get(i).getUser().getFirstName(), result));
                    playerArrayList.remove(i);
                    break;
                }
            }
        }

        for(int i = 0; i < playerArrayList.size(); i++){
            data.add(new ValueDataEntry(playerArrayList.get(i).getUser().getFirstName(), 0));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }%");

        cartesian.animation(true);
        cartesian.title("Player Performance Chart (Yearly)");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }%");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Player");
        cartesian.yAxis(0).title("Performance");

        anyChartView.setChart(cartesian);

    }

}
