package com.xz.ska;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.xz.com.log.LogUtil;
import com.xz.ska.adapter.NewDetailAdapter;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.custom.InfoTop;
import com.xz.ska.custom.SideRecyclerView;
import com.xz.ska.custom.TipsView;
import com.xz.ska.entity.TopInfo;
import com.xz.ska.utils.DatePickerUtil;
import com.xz.ska.utils.TypeUtil;
import com.xz.ska.utils.UpdateListener;
import com.xz.ska.utils.SpacesItemDecorationVertical;
import com.xz.ska.utils.TimeUtil;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private InfoTop topInfo;
    private SideRecyclerView recyclerMoney;
    private NewDetailAdapter adapter;
    private TipsView tipsView;
    private ImageView empty;
    private android.support.design.widget.FloatingActionButton addData;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        topInfo = findViewById(R.id.top_info);
        recyclerMoney = findViewById(R.id.recycler_money);
        tipsView = findViewById(R.id.tips_view);
        addData = findViewById(R.id.add_data);
        empty = findViewById(R.id.empty_view);
        addData.setOnClickListener(this);
    }

    @Override
    public void init_Data() {
        topInfo.setShouRu("233.33");
        topInfo.setZhiChu("3333.03");
        topInfo.setDateChoose(DatePickerUtil.getMonth() + 1 + "", +DatePickerUtil.getDay() + "");
        tipsView.setTips("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈0我是提示哈哈哈哈哈哈哈哈");

        TypeUtil.initType(this);

        init_Recycler();

        //日期选择器
        topInfo.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "年" + (month + 1) + "月" + dayOfMonth + "日";

                LogUtil.w("已选择" + date);
                //获取指定日期的数据细节
                presenter.getDetailData(TimeUtil.getStringToDate(date, "yyyy年MM月dd日"));
                //跟新月份显示
                topInfo.setDateChoose(month + 1 + "", dayOfMonth + "");
                //设定日期选择器默认年月日为选定的年月日
//                DatePickerUtil.setmDay(dayOfMonth);
//                DatePickerUtil.setmMonth(month);
//                DatePickerUtil.setmYear(year);
            }
        });

    }

    private void init_Recycler() {
        recyclerMoney.setLayoutManager(new LinearLayoutManager(this));
        recyclerMoney.addItemDecoration(new SpacesItemDecorationVertical(10));
        adapter = new NewDetailAdapter(this);
        recyclerMoney.setAdapter(adapter);

        presenter.getDetailData();//获取本地数据

        //空提示
        adapter.setUpdateListener(new UpdateListener() {
            @Override
            public void update(long time) {
                presenter.getDetailData(time);
            }
        });
    }

    @Override
    public void showData(Object object) {
        if (object instanceof List) {

            if (((List) object).size() == 0) {
                //数据空
                recyclerMoney.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.GONE);
                recyclerMoney.setVisibility(View.VISIBLE);
                adapter.refresh(((List) object));
            }
        } else if (object instanceof TopInfo) {
            topInfo.setTodayText(((TopInfo) object).getRi_zhipei() + "");
            //待完成-本月的收入与支出
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_data:
                startActivity(new Intent(this, AddActivity.class));
                break;
        }
    }
}
