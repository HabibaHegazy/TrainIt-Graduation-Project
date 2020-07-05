package com.example.ipingpong.views.Reports;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.core.axes.Circular;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;
import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.Days;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.views.Base.BaseReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReportDailyFragment extends BaseReport {


    public ReportDailyFragment(Player player) {
        this.player = player;
        this.report = "daily";
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
    public void CreateGraph(HashMap<String, String> data){

        HashMap<String, String> map = new HashMap<>();

        for (Map.Entry<String, String> i : data.entrySet()) {

            int dayNum = Integer.parseInt(i.getKey());
            Days day = null;

            if (dayNum == Days.Sunday.getValue()) {
                day = Days.Sunday;
                map.put(day.name(), i.getValue());
            }
            else if(dayNum == Days.Monday.getValue()) {
                day = Days.Monday;
                map.put(day.name(), i.getValue());
            }
            else if(dayNum == Days.Tuesday.getValue()) {
                day = Days.Tuesday;
                map.put(day.name(), i.getValue());
            }
            else if(dayNum == Days.Wednesday.getValue()) {
                day = Days.Wednesday;
                map.put(day.name(), i.getValue());
            }
            else if(dayNum == Days.Thursday.getValue()) {
                day = Days.Thursday;
                map.put(day.name(), i.getValue());
            }
            else if(dayNum == Days.Friday.getValue()) {
                day = Days.Friday;
                map.put(day.name(), i.getValue());
            }
            else if(dayNum == Days.Saturday.getValue()) {
                day = Days.Saturday;
                map.put(day.name(), i.getValue());
            }
        }

        ArrayList<String> colors = new ArrayList<>();
        colors.add("#587cca");
        colors.add("#64b5f6");
        colors.add("#455a64");
        colors.add("#1976d2");
        colors.add("#ef6c00");
        colors.add("#C35A48");
        colors.add("#008577");

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(map.values().toArray()));
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(270d);

        Circular xAxis = circularGauge.axis(0)
                .radius(100d)
                .width(1d)
                .fill((Fill) null);
        xAxis.scale()
                .minimum(0d)
                .maximum(100d);
        xAxis.ticks("{ interval: 1 }")
                .minorTicks("{ interval: 1 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(false);
        xAxis.minorTicks().enabled(false);

        Iterator<String> iterator = map.keySet().iterator();

        int x = 0;
        double d = 0d;

        double radius = 120d;
        while (iterator.hasNext()){
            String key = iterator.next();

            circularGauge.label(d)
                    .text(key + ", <span style=\"\">" + map.get(key) +"%</span>")
                    .useHtml(true)
                    .hAlign(HAlign.CENTER)
                    .vAlign(VAlign.MIDDLE);
            circularGauge.label(d)
                    .anchor(Anchor.RIGHT_CENTER)
                    .padding(0d, 10d, 0d, 0d)
                    .height(17d / 2d + "%")
                    .offsetY(radius + "%")
                    .offsetX(0d);

            Bar bar0 = circularGauge.bar(d);
            bar0.dataIndex(d);
            bar0.radius(radius);
            bar0.width(17d);
            bar0.fill(new SolidFill(colors.get(x), 1d));
            bar0.stroke(null);
            bar0.zIndex(5d);

            Bar bar100 = circularGauge.bar(d + 100d);
            bar100.dataIndex(5d);
            bar100.radius(radius);
            bar100.width(17d);
            bar100.fill(new SolidFill("#F5F4F4", 1d));
            bar100.stroke("1 #e5e4e4");
            bar100.zIndex(4d);

            radius -= 15d;
            x++;
            d += 1d;

        }

        circularGauge.margin(50d, 50d, 0d, 0d);
        circularGauge.title()
                .text("Player Daily Report")
                .useHtml(true);
        circularGauge.title().enabled(true);
        circularGauge.title().hAlign(HAlign.CENTER);
        circularGauge.title()
                .padding(0d, 0d, 0d, 0d)
                .margin(0d, 0d, 20d, 0d);

        anyChartView.setChart(circularGauge);

    }
}
