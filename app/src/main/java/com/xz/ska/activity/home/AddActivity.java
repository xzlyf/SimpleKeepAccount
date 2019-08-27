package com.xz.ska.activity.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.adapter.TypeAdapter;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.constan.TypeShouru;
import com.xz.ska.custom.MulKeyBoardDialog;
import com.xz.ska.utils.OnClickItemListener;
import com.xz.ska.utils.SpacesItemDecorationVH;
import com.xz.ska.constan.TypeZhichu;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView recyclerType;
    private TypeAdapter adapter;
    private TextView zhichuLayout;
    private TextView shouruLayout;
    private ImageView back;

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
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        zhichuLayout.setOnClickListener(this);
        shouruLayout.setOnClickListener(this);
    }


    private MulKeyBoardDialog dialog;

    @Override
    public void init_Data() {
        init_recycler();
        setSelect(zhichuLayout, true);
        setSelect(shouruLayout, false);
    }


    private void init_recycler() {
        recyclerType.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerType.addItemDecoration(new SpacesItemDecorationVH(10));
        adapter = new TypeAdapter(this);
        recyclerType.setAdapter(adapter);

        Local.state=0;//还原
        getZhichuList();

        adapter.setOnItemClickListener(new OnClickItemListener() {
            @Override
            public void onClick(int i,String title) {

                dialog = new MulKeyBoardDialog(AddActivity.this, R.style.base_dialog);
                dialog.create();
                dialog.setIcon(i);
                dialog.setTitle(title);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.push_out_addactivity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhichu_recycler:
                if (Local.state != 0) {
                    Local.state = 0;
                    setSelect(zhichuLayout, true);
                    setSelect(shouruLayout, false);
                    getZhichuList();
                }
                break;
            case R.id.shouru_recycler:
                if (Local.state != 1) {
                    Local.state = 1;
                    setSelect(shouruLayout, true);
                    setSelect(zhichuLayout, false);
                    getShouruList();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void setSelect(TextView view, boolean b) {
        if (b) {
            view.setScaleX(1.3f);
            view.setScaleY(1.3f);
        } else {
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
    }

    /**
     * 支出Recycler的数据
     */
    private void getZhichuList() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < TypeZhichu.getLength(); i++) {
            data.add(i);
        }
        adapter.refresh(data);
    }

    /**
     * 收入recycler的数据
     */
    private void getShouruList() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < TypeShouru.getLength(); i++) {
            data.add(i);
        }
        adapter.refresh(data);
    }
}
