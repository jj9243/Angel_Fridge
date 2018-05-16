package com.namjongbin.fridge_angel;

import android.content.BroadcastReceiver;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


public class Alarmer extends BroadcastReceiver {

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        // Bitmap mLargeIconForNoti=BitmapFactory.decodeResource(getResources(),R.drawable.sb);
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(context, CalendarViewer.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mbuilder;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel("kangwoo", "alarm", NotificationManager.IMPORTANCE_DEFAULT);
            notificationmanager.createNotificationChannel(mChannel);
            mbuilder = new NotificationCompat.Builder(context, mChannel.getId());
        } else {
            mbuilder = new NotificationCompat.Builder(context);
        }
        mbuilder.setSmallIcon(R.drawable.wonkim)
                .setContentTitle("유통기한 알림")
                .setContentText("유통기한이 3일 남았습니다.")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationmanager.notify(1, mbuilder.build());
    }

}
