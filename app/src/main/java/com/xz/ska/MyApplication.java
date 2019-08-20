package com.xz.ska;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.xz.com.log.LogConfig;

import org.litepal.LitePalApplication;

import java.util.ArrayList;

public class MyApplication extends LitePalApplication {
    private static Context mContext;
    private ArrayList<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        init_log();
    }

    private void init_log() {
        LogConfig config = LogConfig.getInstance();
        config.setShowLog(true);
        config.setFlag("xz");


    }

    /**
     * @return
     */
    public static Context getInstance() {
        return mContext;
    }


    /**
     * 添加到ArrayList<Activity>
     *
     * @param activity：Activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 退出所有的Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
