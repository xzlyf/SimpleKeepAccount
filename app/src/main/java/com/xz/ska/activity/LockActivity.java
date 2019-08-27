package com.xz.ska.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.utils.MD5Util;

public class LockActivity extends BaseActivity {
    private String TAG = "XZ";
    private TelephonyManager phoneManager;
    private android.widget.TextView tips;
    private android.widget.EditText userPsd;
    private android.widget.Button submit;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_lock;
    }

    @Override
    public void findID() {
        initView();
    }
    private void initView() {
        tips = findViewById(R.id.tips);
        userPsd = findViewById(R.id.user_psd);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+MD5Util.getMD5(userPsd.getText().toString().trim()));
            }
        });
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
//        phoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        Log.d(TAG, "init_Data: "+phoneManager.getDeviceId());
//        Log.d(TAG, "init_Data: "+phoneManager.getLine1Number());
//        Log.d(TAG, "init_Data: "+phoneManager.getSimSerialNumber());
//        Log.d(TAG, "init_Data: "+phoneManager.getSubscriberId());
//        phoneManager.getDeviceId();//序列号IMEI
//        phoneManager.getLine1Number();//手机号码
//        phoneManager.getSimSerialNumber();//手机卡序列号
//        phoneManager.getSubscriberId();//一个相对唯一的手机信息码----IMSI。不会变

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


}
