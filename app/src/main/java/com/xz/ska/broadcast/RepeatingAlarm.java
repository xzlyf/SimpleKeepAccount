package com.xz.ska.broadcast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.xz.ska.R;
import com.xz.ska.activity.MainActivity;

public class RepeatingAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.xz.ska.alarm")) {
            Intent newIntent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(newIntent);
            PendingIntent pi = PendingIntent.getActivity(context,0,newIntent,0);

            String CHANNEL_ID = "channel_id1";
            String CHANNEL_NAME = "channel_name1";
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //安卓8.0弹出通知不一样
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
            builder.setSmallIcon(R.drawable.logo_max)
//                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentTitle("温馨提醒~")
                    .setContentText("是时候该记账了❤")
                    .setContentIntent(pi)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true);

            manager.notify(1,builder.build());


        }
    }
}
