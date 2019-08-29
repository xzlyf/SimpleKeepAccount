package com.xz.ska.activity.home;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeActivity extends BaseActivity {
    private PieChart mChart;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_analyze;
    }

    @Override
    public void findID() {
        mChart = findViewById(R.id.pie_chart);
    }

    @Override
    public void init_Data() {

        presenter.getMonthDetails(System.currentTimeMillis());

        mChart.setUsePercentValues(true);//设置是否使用百分值，默认为false
        mChart.setDescription(null);//描述内容
        List<PieEntry> yVals = new ArrayList<>();
        yVals.add(new PieEntry(28.6f, "有违章"));
        yVals.add(new PieEntry(71.3f, "无违章"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4A92FC"));
        colors.add(Color.parseColor("#ee6e55"));

        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(getColor(R.color.white));
        mChart.setData(pieData);
    }

    @Override
    public void showData(Object object) {

    }
}
