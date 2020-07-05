package com.example.ipingpong.views.Reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.views.Base.BaseReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportMonthlyFragment extends BaseReport {

    public ReportMonthlyFragment(Player player) {
        this.player = player;
        this.report = "monthly";
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
    protected void CreateGraph(HashMap<String, String> map) {
        Cartesian vertical = AnyChart.vertical();

        vertical.animation(true).title("Player Monthly Report");

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("Jan", 0));
        data.add(new CustomDataEntry("Feb", 0));
        data.add(new CustomDataEntry("Mar", 0));
        data.add(new CustomDataEntry("Apr", 0));
        data.add(new CustomDataEntry("May", 0));
        data.add(new CustomDataEntry("Jun", 0));
        data.add(new CustomDataEntry("Jul", 0));
        data.add(new CustomDataEntry("Aug", 0));
        data.add(new CustomDataEntry("Sep", 0));
        data.add(new CustomDataEntry("Oct", 0));
        data.add(new CustomDataEntry("Nov", 0));
        data.add(new CustomDataEntry("Dec", 0));

        for (Map.Entry<String, String> iterator : map.entrySet()) {
            int result = Math.round(Float.parseFloat(iterator.getValue()));

            switch (iterator.getKey()){

                case "1":
                    data.set(1, new CustomDataEntry("Jan", result));
                    break;
                case "2":
                    data.set(2, new CustomDataEntry("Feb", result));
                    break;
                case "3":
                    data.set(3, new CustomDataEntry("Mar", result));
                    break;
                case "4":
                    data.set(4, new CustomDataEntry("Apr", result));
                    break;
                case "5":
                    data.set(5, new CustomDataEntry("May", result));
                    break;
                case "6":
                    data.set(6, new CustomDataEntry("Jun", result));
                    break;
                case "7":
                    data.set(7, new CustomDataEntry("Jul", result));
                    break;
                case "8":
                    data.set(8, new CustomDataEntry("Aug", result));
                    break;
                case "9":
                    data.set(9, new CustomDataEntry("Sep", result));
                    break;
                case "10":
                    data.set(10, new CustomDataEntry("Oct", result));
                    break;
                case "11":
                    data.set(11, new CustomDataEntry("Nov", result));
                    break;
                case "12":
                    data.set(12, new CustomDataEntry("Dec", result));
                    break;
            }

        }


        Set set = Set.instantiate();
        set.data(data);
        Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");

        Bar bar = vertical.bar(barData);
        bar.labels().format("{%Value} %");

        vertical.yScale().minimum(0d);

        vertical.labels(true);

        vertical.tooltip()
                .displayMode(TooltipDisplayMode.UNION)
                .positionMode(TooltipPositionMode.POINT)
                .unionFormat(
                        "function() {\n" +
                                "      return 'Plain: $' + this.points[1].value + ' mln' +\n" +
                                "        '\\n' + 'Fact: $' + this.points[0].value + ' mln';\n" +
                                "    }");

        vertical.interactivity().hoverMode(HoverMode.BY_X);

        vertical.xAxis(true);
        vertical.yAxis(true);
        vertical.yAxis(0).labels().format("${%Value} mln");

        anyChartView.setChart(vertical);
    }

    private class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value) {
            super(x, value);
        }
    }
}
