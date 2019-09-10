package com.xz.ska.activity.setting;

import android.view.View;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_about;
    }

    @Override
    public void findID() {
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public void init_Data() {

    }

    @Override
    public void showData(Object object) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
