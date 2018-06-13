package com.namjongbin.fridge_angel;

/**
 * @brief   Notify application message
 * @details Call service for notification message to status bar. and check user setting for notification
 * @author Kang woo Nam
 */

import android.content.BroadcastReceiver;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class AlarmBroadcast extends BroadcastReceiver {

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    String[] item ;
    String[] date ;
    int ddayFlag = 0;
    int threeDayFlag = 0;
    int expireFlag = 0;
    int year,month,day;
    String title,message;
    boolean isAlarm = false;


    @Override
    public void onReceive(Context context, Intent intent) {//Call onReceive method, when setting time
        //NotificationManager, Call service for message to status bar
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isChecked = sharedPreferences.getBoolean("switch", false);
        String location = sharedPreferences.getString("location", "");

        SharedPreferences wifiInfo = context.getSharedPreferences("WifiInformation", context.MODE_PRIVATE);
        String macAdd = wifiInfo.getString("macAdd", "");

         parseDate(context);

         if(ddayFlag == 1 && threeDayFlag == 1){
           title = "유통기한 알림";
             message = "오늘 유통기한이 만료되는 품목과 3일뒤 만료될 품목이 있습니다 확인해 주세요!";
             isAlarm = true;
         }
         else if(ddayFlag == 1 && threeDayFlag == 0){
              title = "유통기한 알림";
              message = "오늘 유통기한이 만료되는 품목이 있습니다 확인해 주세요!";
              isAlarm = true;
         }
         else if(ddayFlag == 0 && threeDayFlag == 1){
             title = "유통기한 알림";
             message = "3일뒤 유통기한이 만료될 품목이 있습니다 확인해 주세요!";
             isAlarm = true;
         }
         else if(expireFlag == 1){
             title ="유통기한 알림";
             message = "냉장고에 유통기한이 지난 음식이 있습니다 확인해 주세요!";
         }

         System.out.println("*********************************"+expireFlag +" "+ threeDayFlag);
        if(isAlarm || expireFlag == 1){
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
                    .setContentTitle(title)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            Log.d("TAGTAG","확인"+isChecked);
            Log.d("TAGTAG","확인"+location);
            if (isChecked||(isChecked==false&&location.isEmpty())) {
                if (location.equals("어디서나"))
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



    }
    public void parseItem(String item){

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy년MM월dd일");
        String d = date.format(today).toString();

        if(item.contains("년")) {
            year = Integer.parseInt(item.substring(item.indexOf('년') - 4, item.indexOf('년')));
        }
        else
            year = Integer.parseInt(d.substring(d.indexOf('년')-4,d.indexOf('년')));
        if(item.contains("월"))
            month = Integer.parseInt(item.substring(item.indexOf('월')-2,item.indexOf('월')).trim());
        else
            month = Integer.parseInt(d.substring(d.indexOf('월')-2,d.indexOf('월')));
        if(item.contains("일"))
            day = Integer.parseInt(item.substring(item.indexOf('일')-2,item.indexOf('일')).trim());
        else
            day = Integer.parseInt(d.substring(d.indexOf('일')-2,d.indexOf('일')));

    }

    public void parseDate(Context context) {
        Calendar cal = Calendar.getInstance();
        int yearToday = cal.get(cal.YEAR);
        int monthToday = cal.get(cal.MONTH) + 1;
        int dayToday = cal.get(cal.DATE);
        final DBHelper db = new DBHelper(context, "ITEM.db", null, 2);
        String[] foodItem = new String[db.columnNum()];
        if(!(db.getResult().equals("") || db.getResult() == null))
             foodItem = db.getResult().split("\n");

        date = new String[foodItem.length];
        item = new String[foodItem.length];
        for (int i = 0; i < foodItem.length; i++) {
            String[] str = foodItem[i].split(":");
            item[i] = str[0].replaceAll("[0-9]", "");
            date[i] = str[1];
            parseItem(date[i]);

            if(year ==yearToday && month == monthToday && day == dayToday){
                ddayFlag = 1;
            }if(year ==yearToday && month == monthToday && day == dayToday + 3){
                threeDayFlag = 1;
            }else if(year <= yearToday && month <= monthToday && day < dayToday){
                expireFlag = 1;
            }

        }

    }

}
