package com.xz.ska.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xz.ska.broadcast.RepeatingAlarm;

import java.util.Calendar;

/**
 * 闹钟工具
 * 提醒服务
 *
 */
public class AlarmClockUtil {
    public static void setAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 5000);


        Intent intent = new Intent(context, RepeatingAlarm.class);
        intent.setAction("com.xz.ska.alarm");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
        Log.d("xz", "onClick: 执行");
    }

    /**
     * 开启闹钟
     * @param context
     * @param hour
     * @param minute
     */
    public static void setAlarmV2(Context context,int hour,int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(context, RepeatingAlarm.class);
        intent.setAction("com.xz.ska.alarm");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),(24 * 60 * 60 * 1000),pi);
//        am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+5*1000,(24 * 60 * 60 * 1000),pi);
    }

    /**
     * 关闭闹钟服务
     * @param context
     */
    public static void closeAlarm(Context context){
        Intent intent = new Intent(context,RepeatingAlarm.class);
        intent.setAction("com.xz.ska.alarm");
        PendingIntent pi = PendingIntent.getBroadcast(context,0,intent,0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }
}
