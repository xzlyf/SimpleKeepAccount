package com.xz.ska;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xz.ska.adapter.TypeAdapter;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.utils.OnClickItemListener;
import com.xz.ska.utils.SpacesItemDecorationVH;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseActivity {


    private RecyclerView recyclerType;
    private TypeAdapter adapter;

    //虚假键盘高度
    private int kb_height = 0;
    //屏幕高度
    private int sc_height = 0;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_add;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        recyclerType = findViewById(R.id.recycler_type);

    }

    @Override
    public void init_Data() {
        init_hardware();
        init_recycler();

    }

    private void init_hardware() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        sc_height = outMetrics.heightPixels;
    }

    private void init_recycler() {
        recyclerType.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerType.addItemDecoration(new SpacesItemDecorationVH(10));
        adapter = new TypeAdapter(this);
        recyclerType.setAdapter(adapter);

        List<Integer> data = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            data.add(i);
        }
        adapter.refresh(data);
        adapter.refresh(data);
        adapter.refresh(data);
        adapter.refresh(data);
        adapter.refresh(data);
        adapter.refresh(data);
        adapter.refresh(data);
        adapter.setOnItemClickListener(new OnClickItemListener() {
            @Override
            public void onClick(int i) {

//                if (kb_height == 0) {
//                    //获取虚拟键盘高度
//                    kb_height = findViewById(R.id.number_keyboard).getMeasuredHeight();
//                    ViewGroup.LayoutParams param = recyclerType.getLayoutParams();
//                    //用屏幕高度减去虚拟键盘高度等于recycler布局的高度
//                    param.height = sc_height-kb_height;
//                    recyclerType.setLayoutParams(param);
//                }


            }
        });
    }


    @Override
    public void showData(Object object) {


    }


}
