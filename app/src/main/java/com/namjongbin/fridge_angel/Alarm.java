package com.namjongbin.fridge_angel;

/**
 * Created by USER on 2018-05-22.
 *
 * @brief   Notification
 * @details Set Notification, time
 * @author Kang woo Nam
 *
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;



public class Alarm {
    private Context context;
    Calendar calendar = Calendar.getInstance();

    int hour, minute;

    public Alarm(Context context, int h, int m) {
        this.context = context;
        hour = h;
        minute = m;
    }

    public void Alarm() {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcast.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);


        //Notification set to calendar
        if((hour<calendar.get(calendar.HOUR_OF_DAY))||(hour==calendar.get(calendar.HOUR_OF_DAY)&&minute<calendar.get(calendar.MINUTE))) {
            //Toast.makeText(context, "" + calendar.get(calendar.HOUR_OF_DAY) + calendar.get(calendar.MINUTE), Toast.LENGTH_LONG).show();
            calendar.set(Calendar.YEAR, calendar.get(calendar.YEAR));
            calendar.set(Calendar.MONTH, calendar.get(calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH) + 1);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
        }
        else
        {
            calendar.set(Calendar.YEAR, calendar.get(calendar.YEAR));
            calendar.set(Calendar.MONTH, calendar.get(calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
        }
        //Notification reservation
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
}
