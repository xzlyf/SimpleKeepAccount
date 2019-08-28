package com.xz.ska.activity.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.activity.LockActivity;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.custom.CurrencyDialog;
import com.xz.ska.entity.Setting;

public class MySelfActivity extends BaseActivity implements View.OnClickListener {


    private ImageView back;
    private FrameLayout btn1;
    private TextView btn1Cs;
    private FrameLayout btn2;
    private FrameLayout btn3;
    private FrameLayout btn4;
    private TextView btn3_sp;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_my_self;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        back.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn1Cs = findViewById(R.id.btn1_cs);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn3_sp = findViewById(R.id.btn3_sp);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    @Override
    public void init_Data() {
        btn1Cs.setText("已选择：" + Local.moneySymbol);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getUserSetting();
    }

    @Override
    public void showData(Object object) {
        if (object instanceof Setting) {

            Setting setting = (Setting) object;
            if (setting.isShow()) {
                btn3_sp.setText(setting.getShowString());
            } else {
                btn3_sp.setText("");
            }
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_in_mainactivity, R.anim.push_out_myselfactivity);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn1:
                currency();
                break;
            case R.id.btn2:
                startActivity(new Intent(MySelfActivity.this, CategoryActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(MySelfActivity.this, AlarmSettingActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(MySelfActivity.this, LockActivity.class).putExtra("isHome",false));
                break;


        }
    }

    /**
     * 切换货币
     */
    private void currency() {
        CurrencyDialog dialog = new CurrencyDialog(this, R.style.base_dialog);
        dialog.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                btn1Cs.setText("已选择：" + Local.moneySymbol);
            }
        });
        dialog.show();

    }
}
