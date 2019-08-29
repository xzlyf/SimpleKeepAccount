package com.xz.ska.activity.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.custom.CurrencyDialog;
import com.xz.ska.custom.UpdateDialog;
import com.xz.ska.entity.Setting;
import com.xz.ska.entity.UpdateServer;
import com.xz.ska.utils.PackageUtil;
import com.xz.ska.utils.SharedPreferencesUtil;

public class MySelfActivity extends BaseActivity implements View.OnClickListener {


    private ImageView back;
    private FrameLayout btn1;
    private TextView btn1Cs;
    private FrameLayout btn2;
    private FrameLayout btn3;
    private TextView btn3_sp;
    private FrameLayout btn4;
    private FrameLayout btn5;
    private FrameLayout btn7;
    private TextView btn7Sp;
    private Switch btn6;
    private EditText tipsEdit;

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
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        tipsEdit = findViewById(R.id.btn6_sp);
        btn3_sp = findViewById(R.id.btn3_sp);
        btn7Sp = findViewById(R.id.btn7_sp);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);


    }

    @Override
    public void init_Data() {
        //设置属性状态
        btn1Cs.setText("已选择：" + Local.moneySymbol);

        btn6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeState(isChecked);

            }
        });
        //本地版本代号
        btn7Sp.setText("v"+PackageUtil.getVersionName(this));


    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.getUserSetting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferencesUtil.saveBoolean(this, "state", "tips_switch", btn6.isChecked());
        SharedPreferencesUtil.saveString(this, "state", "tips_text", tipsEdit.getText().toString());
        Local.tipsSwitch = btn6.isChecked();
        Local.tipsText = tipsEdit.getText().toString();

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

            btn6.setChecked(Local.tipsSwitch);
            changeState(Local.tipsSwitch);
        } else if (object instanceof UpdateServer) {
            dismissLoading();

            if (Local.code > PackageUtil.getVersionCode(this)) {
                /**
                 * 检查更新
                 */
                UpdateDialog dialog = new UpdateDialog(MySelfActivity.this, R.style.update_dialog);
                dialog.create();
                dialog.setMsg(((UpdateServer) object).getMsg());
                dialog.setVersionName(((UpdateServer) object).getName());
                dialog.setDownloadLink(((UpdateServer) object).getLink());
                dialog.setLevel(((UpdateServer) object).getLevel());
                dialog.show();
            }else{
                mToast("最新版本啦");
            }
        } else if (object instanceof String) {
            mToast(object.toString());
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
                startActivity(new Intent(MySelfActivity.this, LockActivity.class).putExtra("isHome", false));
                break;
            case R.id.btn5:
                startActivity(new Intent(MySelfActivity.this, BackupActivity.class));
                break;
            case R.id.btn7:
                update();
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

    /**
     * @param b
     */
    private void changeState(boolean b) {
        if (b) {
            tipsEdit.setVisibility(View.VISIBLE);
            tipsEdit.setText(Local.tipsText);
        } else {
            tipsEdit.setVisibility(View.GONE);
        }
    }

    private void update() {
        //判断是否已经读取过更新信息了
        if (Local.level != -1) {
            //远端版本号大于本地版本号显示对话框
            if (Local.code > PackageUtil.getVersionCode(this)) {
                //有信息直接显示了
                UpdateDialog dialog = new UpdateDialog(MySelfActivity.this, R.style.update_dialog);
                dialog.create();
                dialog.setMsg(Local.msg);
                dialog.setVersionName(Local.name);
                dialog.setDownloadLink(Local.link);
                dialog.setLevel(Local.level);
                dialog.show();
            }else{
                mToast("最新版本啦");
            }
        } else {
            showLoading();
            presenter.updateCheck();
        }

    }


    /**
     * ===========================================================================================
     * 点击非编辑区域收起键盘
     * 获取点击事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
            //return !(ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * ============================================================================================
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
