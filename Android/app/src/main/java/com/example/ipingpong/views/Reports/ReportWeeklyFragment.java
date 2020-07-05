package com.example.ipingpong.views.Reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.views.Base.BaseReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportWeeklyFragment extends BaseReport {



    public ReportWeeklyFragment(Player player) {
        this.player = player;
        this.report = "weekly";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(progressBar);

        createReport(report, player);
        return view;
    }

    @Override
    protected void CreateGraph(HashMap<String, String> data) {
        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> map = new ArrayList<>();

        int i = 1;
        for (Map.Entry<String, String> iterator : data.entrySet()) {
            int result = Math.round(Float.parseFloat(iterator.getValue()));
            map.add(new ValueDataEntry("Week " + i, result));
            i++;
        }

        pie.data(map);

        pie.title("Player Weekly Report");

        pie.legend().padding(0d, 0d, 0d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

}
