package com.xz.ska;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xz.ska.base.BaseActivity;
import com.xz.ska.custom.InfoTop;

public class MainActivity extends BaseActivity {

    private InfoTop topInfo;
    private RecyclerView recyclerView;
    private TextView tips;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void findID() {
        topInfo = findViewById(R.id.top_info);
        recyclerView = findViewById(R.id.recycler_money);
        tips = findViewById(R.id.tips);
    }

    @Override
    public void init_Data() {
        topInfo.setShouRu("233.33");
        topInfo.setTodayText("23.00");
        topInfo.setZhiChu("3333.03");
        //跑马灯效果
        tips.setSelected(true);
        init_Recycler();
    }

    private void init_Recycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showData(Object object) {

    }
}
