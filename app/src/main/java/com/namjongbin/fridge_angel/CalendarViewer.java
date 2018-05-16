package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.example.user.termproject.BroadcastD;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

public class CalendarViewer extends Activity {
    private static int ONE_MINUTE = 5626;
    Button alarmSetButton;
    Button youtubeButton;

    int y = -1;
    int m = -1;
    int d = -1;

    Calendar calendar = Calendar.getInstance();

    String sfName="myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

//쉐어드프리퍼런스 테스트
        SharedPreferences sf=getSharedPreferences(sfName,0);
        Toast.makeText(getApplicationContext(),"저장되있던 것"+sf.getString("name",""),Toast.LENGTH_LONG).show();
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        //Toast.makeText(getApplicationContext(),""+calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.MONTH)+" "+calendar.get(Calendar.DAY_OF_MONTH),Toast.LENGTH_LONG).show();
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
                .setMaximumDate(CalendarDay.from(2019, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getApplicationContext(), "" + date.getYear() + "년 " + (date.getMonth() + 1) + "월 " + date.getDay() + "일 입니다.", Toast.LENGTH_LONG).show();
                y = date.getYear();
                m = date.getMonth();
                d = date.getDay();
            }
        });

        alarmSetButton = (Button) findViewById(R.id.alarmsetbtn);

        alarmSetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (y == -1 || m == -1 || d == -1)
                    Toast.makeText(getApplicationContext(), "날짜를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                else {
                    new AlarmHATT(getApplicationContext()).Alarm();
                }
            }
        });

        youtubeButton=(Button)findViewById(R.id.youtubebtn);

        youtubeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");

                intent.putExtra("query", "석빈");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

    }

    //쉐어드프리퍼런스 테스트
    protected void onStop() {
        super.onStop();

        SharedPreferences sf=getSharedPreferences(sfName,0);
        SharedPreferences.Editor editor=sf.edit();
        String str=Integer.toString(y)+"년 "+Integer.toString(m)+"월 "+Integer.toString(d)+"일 알람예정입니다.";
        editor.putString("name",str);
        editor.commit();
    }


    public class AlarmHATT {
        private Context context;

        public AlarmHATT(Context context) {
            this.context = context;
        }

        public void Alarm() {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(CalendarViewer.this, Alarmer.class);

            PendingIntent sender = PendingIntent.getBroadcast(CalendarViewer.this, 0, intent, 0);


            //알람시간 calendar에 set해주기

            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            calendar.set(Calendar.MINUTE, 19);
            calendar.set(Calendar.SECOND, 0);

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            Toast.makeText(getApplicationContext(), "알람 설정 완료 : " + calendar.getTime(), Toast.LENGTH_LONG).show();
        }
    }


    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

}

