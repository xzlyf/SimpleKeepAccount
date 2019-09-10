package com.xz.ska.activity.home;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.TypeColor;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeActivity extends BaseActivity implements View.OnClickListener {
    private PieChart pieChart;
    private ImageView back;
    private TextView title;
    private PieDataSet pieDataSet;
    private PieData pieData;
    private Button zhichuBtn, shouruBtn;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_analyze;
    }

    @Override
    public void findID() {
        pieChart = findViewById(R.id.pie_chart);
        back = findViewById(R.id.back);
        title = findViewById(R.id.tv_title);
        back.setOnClickListener(this);
        zhichuBtn = findViewById(R.id.zhichu_btn);
        zhichuBtn.setOnClickListener(this);
        shouruBtn = findViewById(R.id.shouru_btn);
        shouruBtn.setOnClickListener(this);
    }

    @Override
    public void init_Data() {
        //获取圆环数据数据
        presenter.getPieChar(System.currentTimeMillis());
//        presenter.getPieChar_shouru(System.currentTimeMillis());


        pieChart.setDescription(null);//描述内容
        pieChart.animateXY(1000, 1000);//动画
        pieChart.setDragDecelerationFrictionCoef(0.9f);//转盘摩檫力
        //描述文字
        Description ds = new Description();
        ds.setText("");
        ds.setTextSize(14f);
        ds.setTextColor(getColor(R.color.glossDark));
        pieChart.setDescription(ds);
        //获取图例
        Legend legend = pieChart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);  //设置图例水平显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); //顶部
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT); //右对其
        legend.setXEntrySpace(7f);//x轴的间距
        legend.setYEntrySpace(25f); //y轴的间距
        legend.setYOffset(10f);  //图例的y偏移量
        legend.setXOffset(10f);  //图例x的偏移量
        legend.setTextColor(getColor(R.color.glossDark)); //图例文字的颜色
        legend.setTextSize(14);  //图例文字的大小

        //在圆环外显示数值
        pieDataSet = new PieDataSet(null, "");
        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
        pieDataSet.setValueLinePart1Length(0.4f);
        pieDataSet.setValueLinePart2Length(0.4f);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieData = new PieData();

        //默认选择支出按钮
        reView(shouruBtn, zhichuBtn);

    }

    private void reView(View v1, View v2) {
        v1.setBackground(getResources().getDrawable(R.drawable.selector_btn));//有显示
        v2.setBackground(getResources().getDrawable(R.drawable.selector_btn_f));//无显示
    }

    @Override
    public void showData(Object object) {
        if (object instanceof List) {

            if (((List) object).size() == 0) {
                pieChart.setVisibility(View.GONE);
                title.setText("本月无账单");
                return;
            }


            List<Integer> colors = new ArrayList<>();
            for (int i = 0; i < ((List) object).size(); i++) {
                colors.add(TypeColor.getRandomColor());
            }
            pieData.clearValues();
            pieDataSet.clear();
//            pieDataSet = new PieDataSet(, "");
            pieDataSet.setValues((List<PieEntry>) object);
            pieDataSet.setLabel("");
            pieData.setDataSet(pieDataSet);
            pieData.setValueTextSize(12f);
            pieData.setValueTextColor(getColor(R.color.glossDark));
            pieDataSet.setColors(colors);
            pieChart.setData(pieData);
            pieChart.notifyDataSetChanged();
            pieChart.invalidate();
            pieChart.animateXY(1000, 1000);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.zhichu_btn:
                presenter.getPieChar(System.currentTimeMillis());
                reView(shouruBtn, zhichuBtn);

                break;
            case R.id.shouru_btn:
                presenter.getPieChar_shouru(System.currentTimeMillis());
                reView(zhichuBtn, shouruBtn);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_in_mainactivity_2, R.anim.push_out_analyze);

    }


}
