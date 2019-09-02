package com.xz.ska.activity.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.custom.CurrencyDialog;
import com.xz.ska.custom.UpdateDialog;
import com.xz.ska.entity.Book;
import com.xz.ska.entity.Setting;
import com.xz.ska.entity.UpdateServer;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.PackageUtil;
import com.xz.ska.utils.SharedPreferencesUtil;

public class MySelfActivity extends BaseActivity implements View.OnClickListener {


    private TextView btn1Cs;
    private TextView btn3_sp;
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
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        btn6 = findViewById(R.id.btn6);
        btn1Cs = findViewById(R.id.btn1_cs);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);
        tipsEdit = findViewById(R.id.btn6_sp);
        btn3_sp = findViewById(R.id.btn3_sp);
        btn7Sp = findViewById(R.id.btn7_sp);
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
        btn7Sp.setText("v" + PackageUtil.getVersionName(this));


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
            } else {
                mToast("最新版本啦");
            }
        } else if (object instanceof String) {
            mToast(object.toString());
            dismissLoading();
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
            case R.id.btn8:
                mToast("没有意见-_-");
                break;
            case R.id.btn9:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.create();
                builder.setTitle("警告");
                builder.setMessage("即将删除所有账本，请慎重选择，是否继续！");
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LitePalUtil.deleteAll(Book.class);
                        Toast.makeText(MySelfActivity.this, "清空完成", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
                break;

            case R.id.btn10:
                startActivity(new Intent(MySelfActivity.this, FamilyActivity.class));
                break;
            case R.id.btn11:
                Intent intent = new Intent(Intent.ACTION_MEDIA_SHARED);
                intent.setType("测试测试测试测试测试测试测试测试测试测试123321123456776543211123");  //纯文本
                /*图片分享
                　　　　it.setType("image/png");
                　　　　　//添加图片
             　　　　 File f = new File(Environment.getExternalStorageDirectory()+"/name.png");
         　　　　　　
         　　　　 Uri uri = Uri.fromFile(f);
         　　　　 intent.putExtra(Intent.EXTRA_STREAM, uri);
        　　　　　*/

                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, "I would like to share this with you...");
                startActivity(Intent.createChooser(intent, getTitle()));
                break;
            case R.id.btn12:
                startActivity(new Intent(MySelfActivity.this, AboutActivity.class));
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
            } else {
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
