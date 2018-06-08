package com.namjongbin.fridge_angel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by USER on 2018-05-22.
 */

public class Alarm{
    private Context context;
    Calendar calendar = Calendar.getInstance();

    int hour,minute;
    public Alarm(Context context,int h, int m)
    {
        this.context = context;
        hour=h;
        minute=m;
    }

    public void Alarm() {
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmBroadcast.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);


        //알람시간 calendar에 set해주기

        calendar.set(Calendar.YEAR,  calendar.get(calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        //알람 예약
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60*1000, sender);
        //am.cancel(sender);
       // Toast.makeText(context, "알람 설정 완료 : " + calendar.getTime(), Toast.LENGTH_LONG).show();
    }
}
