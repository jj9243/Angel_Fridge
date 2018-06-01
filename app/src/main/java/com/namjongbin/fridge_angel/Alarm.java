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

    int year, month, day;
    public Alarm(Context context,int y, int m, int d)
    {
        this.context = context;
        year=y;
        month=m;
        day=d;
    }

    public void Alarm() {
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmBroadcast.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);


        //알람시간 calendar에 set해주기

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 03);
        calendar.set(Calendar.SECOND, 0);

        //알람 예약
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        Toast.makeText(context, "알람 설정 완료 : " + calendar.getTime(), Toast.LENGTH_LONG).show();
    }
}
