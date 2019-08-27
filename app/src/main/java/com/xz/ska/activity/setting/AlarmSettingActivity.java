package com.xz.ska.activity.setting;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.utils.AlarmClockUtil;
import com.xz.ska.utils.SharedPreferencesUtil;

import at.markushi.ui.CircleButton;

public class AlarmSettingActivity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private TextView alarmTips;
    private SwitchCompat alarmSwitch;
    private TimePicker timeSelect;
    private boolean amSwitch;
    private int mHour, mMinute;
    private CircleButton submit;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_alarm_setting;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        alarmTips = findViewById(R.id.alarm_tips);
        alarmSwitch = findViewById(R.id.alarm_switch);
        timeSelect = findViewById(R.id.time_select);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);

    }


    @Override
    public void init_Data() {
        //获取存储的状态
        amSwitch = SharedPreferencesUtil.getBoolean(this, "alarm", "mswitch", false);
        alarmSwitch.setChecked(amSwitch);
        timeSelect.setIs24HourView(true);//设置24小时制
        mHour = timeSelect.getHour();
        mMinute = timeSelect.getMinute();
        alarmTips.setText("我将每天" + mHour + ":" + mMinute + "分提醒您记账");
        alarmTips.setVisibility(View.INVISIBLE);


        init_listeners();

        //显示用户存储的时间
        if (amSwitch) {
            mHour = SharedPreferencesUtil.getInt(this,"alarm","mhour",23);
            mMinute = SharedPreferencesUtil.getInt(this,"alarm","minute",23);
            timeSelect.setHour(mHour);
            timeSelect.setMinute(mMinute);
            alarmTips.setVisibility(View.VISIBLE);

        }


    }

    private void init_listeners() {
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                amSwitch = isChecked;
                if (isChecked) {
                    alarmTips.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                } else {
                    alarmTips.setVisibility(View.INVISIBLE);
                }
            }
        });
        timeSelect.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarmTips.setText("我将每天" + hourOfDay + ":" + minute + "分提醒您记账");
            }
        });
    }

    @Override
    public void showData(Object object) {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                SharedPreferencesUtil.saveBoolean(this, "alarm", "mswitch", amSwitch);

                if (amSwitch) {
                    SharedPreferencesUtil.saveInt(this,"alarm","mhour",timeSelect.getHour());
                    SharedPreferencesUtil.saveInt(this,"alarm","minute",timeSelect.getMinute());
                    //开启服务
                    AlarmClockUtil.setAlarmV2(this,timeSelect.getHour(),timeSelect.getMinute());
//                    mToast("已设置提醒服务");
                    finish();
                }else{
                    //注销服务
                    AlarmClockUtil.closeAlarm(this);
//                    mToast("已取消 提醒服务");
                    finish();


                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
