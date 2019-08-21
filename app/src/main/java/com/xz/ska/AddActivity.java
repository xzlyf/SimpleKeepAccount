package com.xz.ska;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xz.com.log.LogUtil;
import com.xz.ska.adapter.TypeAdapter;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.custom.MulKeyBoardDialog;
import com.xz.ska.utils.OnClickItemListener;
import com.xz.ska.utils.SpacesItemDecorationVH;
import com.xz.ska.utils.TypeUtil;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView recyclerType;
    private TypeAdapter adapter;
    private TextView zhichuLayout;
    private TextView shouruLayout;
    private int state=0;//0支出  1收入

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
        zhichuLayout = findViewById(R.id.zhichu_recycler);
        shouruLayout = findViewById(R.id.shouru_recycler);

        zhichuLayout.setOnClickListener(this);
        shouruLayout.setOnClickListener(this);

    }

    private MulKeyBoardDialog dialog;

    @Override
    public void init_Data() {
        init_recycler();
        setSelect(zhichuLayout,true);
        setSelect(shouruLayout,false);
    }


    private void init_recycler() {
        recyclerType.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerType.addItemDecoration(new SpacesItemDecorationVH(10));
        adapter = new TypeAdapter(this);
        recyclerType.setAdapter(adapter);

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < TypeUtil.getLength(); i++) {
            data.add(i);
        }
        adapter.refresh(data);

        adapter.setOnItemClickListener(new OnClickItemListener() {
            @Override
            public void onClick(int i) {

                dialog = new MulKeyBoardDialog(AddActivity.this, R.style.base_dialog);
                dialog.create();
                dialog.setIcon(i);
                dialog.show();


            }
        });
    }


    @Override
    public void showData(Object object) {


    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhichu_recycler:
                state = 0;
                setSelect(zhichuLayout,true);
                setSelect(shouruLayout,false);
                break;
            case R.id.shouru_recycler:
                state = 1;
                setSelect(shouruLayout,true);
                setSelect(zhichuLayout,false);
                break;
        }
    }
    private void setSelect(TextView view,boolean b){
        if (b){
            view.setScaleX(1.3f);
            view.setScaleY(1.3f);
        }else{
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
    }
}
