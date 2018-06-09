package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.content.BroadcastReceiver;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;


public class AlarmBroadcast extends BroadcastReceiver {

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    String[] item, date;


    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        // Bitmap mLargeIconForNoti=BitmapFactory.decodeResource(getResources(),R.drawable.sb);

        //SwitchPreference notyOnOff = (SwitchPreference) findPreference("switch");
        //String name;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isChecked = sharedPreferences.getBoolean("switch", false);
        String location = sharedPreferences.getString("location", "");

        SharedPreferences wifiInfo = context.getSharedPreferences("WifiInformation", context.MODE_PRIVATE);
        //String wifiName=wifiInfo.getString("wifiName","");
        String macAdd = wifiInfo.getString("macAdd", "");

        //String
        /*
        if (isChecked)
            name = "됐어!";
        else
            name = "안됐어ㅠ";
            */
        final DBHelper db = new DBHelper(context, "ITEM.db", null, 2);
         parseDate(context);
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notiIntent = new Intent(context, MainActivity.class);
        notiIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mbuilder;

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel("kangwoo", "alarm", NotificationManager.IMPORTANCE_DEFAULT);
            notificationmanager.createNotificationChannel(mChannel);
            mbuilder = new NotificationCompat.Builder(context, mChannel.getId());
        } else {
            mbuilder = new NotificationCompat.Builder(context);
        }
        mbuilder.setSmallIcon(R.drawable.fridgeicon)
                .setContentTitle("유통기한 알림")
                .setContentText("유통기한이 얼마 남지 않은 품목이 있습니다. 확인해주세요!")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        Log.d("TAGTAG","확인"+isChecked);
        Log.d("TAGTAG","확인"+location);
        if (isChecked||(isChecked==false&&location.isEmpty())) {
            if (location.equals("Anywhere"))
                notificationmanager.notify(1, mbuilder.build());
            else {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String add = wifi.getConnectionInfo().getBSSID();

                if (add != null)
                    if (add.equals(macAdd))
                        notificationmanager.notify(1, mbuilder.build());
            }
        }


    }


    public void parseDate(Context context) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int day = cal.get(cal.DATE);
        System.out.println("*****************************\n현재 연도 " + year + "현재 월 " + month + "현재 일" + day);
        final DBHelper db = new DBHelper(context, "ITEM.db", null, 2);

        String[] foodItem = db.getExpiredItem(year, month, day).split("\n");
        String[] str = new String[foodItem.length];
        date = new String[foodItem.length];
        item = new String[foodItem.length];
        System.out.println("******************************");
        for (int i = 0; i < foodItem.length-1; i++) {
            String temp = "";
            str = foodItem[i].split(":");
            item[i] = str[0].replaceAll("[0-9]", "");
            date[i] = str[1];
            System.out.println("유통기한 지난 품목" + item[i] + date[i]);
        }
    }

}