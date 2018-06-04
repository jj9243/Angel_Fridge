package com.namjongbin.fridge_angel;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by USER on 2018-06-02.
 */

public class TimePreference extends DialogPreference {

    private int lastHour = 0;
    private int lastMinute = 0;
    private TimePicker picker = null;
    Context c;

    public TimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        Log.d("asd","ssssss");
        setPositiveButtonText("설정");
        setNegativeButtonText("취소");
    }


    public static int getHour(String time) {
        String[] pieces = time.split(":");
        return (Integer.parseInt(pieces[0]));
    }

    public static int getMinute(String time) {
        String[] pieces = time.split(":");
        return (Integer.parseInt(pieces[1]));
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        return (picker);
       // Log.d("asdasd","크라리잇다이얼로그뷰");
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.setCurrentHour(lastHour);
        picker.setCurrentMinute(lastMinute);
        Log.d("asdasd","바인드다이얼로그뷰");
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);


        if (positiveResult) {
            lastHour = picker.getCurrentHour();
            lastMinute = picker.getCurrentMinute();
            new Alarm(getContext(), lastHour, lastMinute).Alarm();
            String time;
            if (lastMinute < 10) {
                time = String.valueOf(lastHour) + ":0" + String.valueOf(lastMinute);
                setSummary(time);
            } else {
                time = String.valueOf(lastHour) + ":" + String.valueOf(lastMinute);
                setSummary(time);
            }

            Toast.makeText(getContext(), "알람 시간 설정 완료  " + time, Toast.LENGTH_LONG).show();
            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time = null;
Log.d("asdasd","이니셜밸류");
        if (restoreValue) {
            if (defaultValue == null) {
                time = getPersistedString("00:00");
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }

        lastHour = getHour(time);
        lastMinute = getMinute(time);

    }

    public String getTime()
    {
        if(lastMinute<10)
        return ""+Integer.toString(lastHour)+":0"+Integer.toString(lastMinute);
        else
            return ""+Integer.toString(lastHour)+":"+Integer.toString(lastMinute);
    }
}
