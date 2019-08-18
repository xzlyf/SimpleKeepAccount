package com.xz.ska;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xz.ska.base.BaseActivity;
import com.xz.ska.custom.InfoTop;
import com.xz.ska.custom.TipsView;
import com.xz.ska.entity.Book;
import com.xz.ska.sql.LitePalUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private InfoTop topInfo;
    private RecyclerView recyclerMoney;
    private TipsView tipsView;
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
        addData.setOnClickListener(this);
    }

    @Override
    public void init_Data() {
        topInfo.setShouRu("233.33");
        topInfo.setTodayText("23.00");
        topInfo.setZhiChu("3333.03");
        tipsView.setTips("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈0我是提示哈哈哈哈哈哈哈哈");

        init_Recycler();


    }

    private void init_Recycler() {
        recyclerMoney.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showData(Object object) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_data:
                startActivity(new Intent(this,AddActivity.class));
                break;
        }
    }
}
