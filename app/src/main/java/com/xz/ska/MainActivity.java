package com.xz.ska;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xz.ska.base.BaseActivity;
import com.xz.ska.custom.InfoTop;
import com.xz.ska.custom.TipsView;

public class MainActivity extends BaseActivity {


    private InfoTop topInfo;
    private TipsView tips;
    private RecyclerView recyclerMoney;

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
        tips = findViewById(R.id.tips_view);
        recyclerMoney = findViewById(R.id.recycler_money);
    }

    @Override
    public void init_Data() {
        topInfo.setShouRu("233.33");
        topInfo.setTodayText("23.00");
        topInfo.setZhiChu("3333.03");
//        tips.setTips("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈0我是提示哈哈哈哈哈哈哈哈");
        init_Recycler();
    }

    private void init_Recycler() {
        recyclerMoney.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showData(Object object) {

    }


}
