package com.xz.ska.activity.setting;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;

import com.xz.ska.R;
import com.xz.ska.activity.MainActivity;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.entity.CFAS;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.MD5Util;
import com.xz.ska.utils.RandomString;

import at.markushi.ui.CircleButton;

public class LockActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "XZ";
    private TelephonyManager phoneManager;
    private TextView tips;
    private EditText userPsd;
    private CircleButton submit;
    private ImageView back;
    private TextView title;
    private int state; //0 无密码  1有
    private String IMEI;
    private CFAS cfas;
    private TextView retips;
    private EditText userRepsd;
    private Group loginGroup;
    private FrameLayout switchLayout;
    private SwitchCompat switchView;
    private String userK;
    private boolean isHome;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_lock;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        loginGroup = findViewById(R.id.group_login);
        tips = findViewById(R.id.tips);
        userPsd = findViewById(R.id.user_psd);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.title);
        retips = findViewById(R.id.retips);
        userRepsd = findViewById(R.id.user_repsd);
        switchLayout = findViewById(R.id.switch_lock);
        switchView = findViewById(R.id.switch_view);
        switchLayout.setOnClickListener(this);
        switchView.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void init_Data() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.create();
            builder.setTitle("注意");
            builder.setMessage("需要获取设备信息才能进行下一步，否者将不可使用该功能。");
            builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(LockActivity.this,
                            new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }
            });
            builder.show();
            return;
        }

        isHome = getIntent().getBooleanExtra("isHome", false);

        switchLayout.setVisibility(View.INVISIBLE);
        phoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        Log.d(TAG, "init_Data: "+phoneManager.getDeviceId());
//        Log.d(TAG, "init_Data: "+phoneManager.getLine1Number());
//        Log.d(TAG, "init_Data: "+phoneManager.getSimSerialNumber());
//        Log.d(TAG, "init_Data: "+phoneManager.getSubscriberId());
        IMEI = phoneManager.getDeviceId();//序列号IMEI
//        phoneManager.getLine1Number();//手机号码
//        phoneManager.getSimSerialNumber();//手机卡序列号
//        phoneManager.getSubscriberId();//一个相对唯一的手机信息码----IMSI。不会变

        //判断是否已设置过pass
        cfas = (CFAS) LitePalUtil.queryFirst(CFAS.class);
        if (cfas == null) {
            tips.setText("第一次运行将创建密码");
            state = 0;
            retips.setVisibility(View.VISIBLE);
            userRepsd.setVisibility(View.VISIBLE);
        } else {
            state = 1;
            retips.setVisibility(View.GONE);
            userRepsd.setVisibility(View.GONE);
        }

    }

    @Override
    public void showData(Object object) {

    }

    /**
     * 权限结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                    init_Data();
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if (state == 0) {

                    if (!userPsd.getText().toString().trim().equals(userRepsd.getText().toString().trim())) {
                        mToast("两次密码不一样");
                        return;
                    }

                    //写入操作
                    String rule = MD5Util.getMD5(userPsd.getText().toString().trim());
                    String date = System.currentTimeMillis() + "";
                    String ramdon1 = RandomString.getRandomString(32);
                    String ramdon2 = RandomString.getRandomString(16);
                    String ramdon3 = RandomString.getRandomString(32);
                    String ramdon4 = RandomString.getRandomString(8);
                    String pw = MD5Util.getMD5(rule + ramdon3);//k2+k5
//                    Log.d(TAG, "onClick: " + rule);
//                    Log.d(TAG, "onClick: " + date);
//                    Log.d(TAG, "onClick: " + ramdon1);
//                    Log.d(TAG, "onClick: " + ramdon2);
//                    Log.d(TAG, "onClick: " + ramdon3);
//                    Log.d(TAG, "onClick: " + ramdon4);
//                    Log.d(TAG, "onClick: " + pw);

                    LitePalUtil.saveCFAS(date, pw, ramdon1, ramdon2, ramdon3, ramdon4);
                    //复原
                    state = 1;
                    tips.setText("请输入密码");
                    retips.setVisibility(View.GONE);
                    userRepsd.setVisibility(View.GONE);
                    userPsd.setText("");

                } else if (state == 1) {
                    //登录操作
                    if (cfas == null) {
                        //再查询一边
                        cfas = (CFAS) LitePalUtil.queryFirst(CFAS.class);
                        if (cfas == null) {
                            return;
                        }
                    }
                    String rule = MD5Util.getMD5(userPsd.getText().toString().trim());
                    userK = MD5Util.getMD5(rule + cfas.getK5());
                    if (cfas.getK2().equals(userK)) {
                        tips.setText("成功");

                        if (isHome) {
                            startActivity(new Intent(LockActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("isSkip", true));

                            return;
                        }

                        loginGroup.setVisibility(View.GONE);
                        switchLayout.setVisibility(View.VISIBLE);
                        switchView.setChecked(true);
                    } else {
                        tips.setText("失败");
                    }


                }
                break;
            case R.id.switch_lock:
                switchChoose();
                break;
            case R.id.switch_view:
                switchChoose();
                break;
        }
    }

    private void switchChoose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle("注意");
        builder.setMessage("关闭密码锁将删除原来的密码，以后进入软件将无需输入密码。");
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                LitePalUtil.delete(CFAS.class, "k2 = ?", userK);
                mToast("取消成功");
                finish();

            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                switchView.setChecked(true);
            }
        });
        builder.show();
    }
}
