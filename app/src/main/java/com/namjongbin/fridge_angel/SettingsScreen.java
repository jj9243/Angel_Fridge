package com.namjongbin.fridge_angel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.Set;

public class SettingsScreen extends PreferenceFragment {

    EditTextPreference user;
    SwitchPreference notyOnOff;
    ListPreference notyLocation;
    SharedPreferences wifiInfo;
    WifiDialog wifiDialog;
    TimePreference timePreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);

        user = (EditTextPreference) findPreference("user");
        notyOnOff = (SwitchPreference) findPreference("switch");
        notyLocation = (ListPreference) findPreference("location");
        wifiDialog = (WifiDialog) findPreference("wifi");
        timePreference = (TimePreference) findPreference("time");

      //  Toast.makeText(getActivity().getApplicationContext(), "상태 :" + wifi, Toast.LENGTH_LONG).show();

        if (user.getText() != null)
            user.setSummary(user.getText().toString());
        Log.d("TAGTAG", "" + notyLocation.getValue());
        if (notyLocation.getValue() != null) {
            notyLocation.setSummary(notyLocation.getValue());
        }

        wifiInfo = getActivity().getSharedPreferences("WifiInformation", getActivity().MODE_PRIVATE);
        wifiDialog.setSummary(wifiInfo.getString("wifiName",""));

        timePreference.setSummary(timePreference.getTime());
        //Toast.makeText(getActivity().getApplicationContext(),"",Toast.LENGTH_LONG).show();
       //


        user.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String name = newValue.toString();
                preference.setSummary(name);
                return true;
            }
        });


        notyOnOff.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (notyOnOff.isChecked())
                    cancelJob();
                else {
                    AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity(), AlarmBroadcast.class);

                    PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 2, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), sender);
                    Toast.makeText(getActivity().getApplicationContext(), "켜졌어요" + notyOnOff.isChecked(), Toast.LENGTH_LONG).show();

                    Log.d("TAGTAG", "" + notyLocation.getSummary());
                    if (notyLocation.getSummary().equals("In home")) {

                        scheduleJob();
                    }
                }
                return true;
            }
        });

        notyLocation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String where = newValue.toString();
                preference.setSummary(where);
                preference.setDefaultValue(newValue);
                if (where.equals("In home")) {
                    scheduleJob();
                    Toast.makeText(getActivity().getApplicationContext(), "와이파이 체킹 시작!", Toast.LENGTH_LONG).show();
                } else {
                    cancelJob();
                    Toast.makeText(getActivity().getApplicationContext(), "와이파이 체킹 종료!", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }


    public void scheduleJob() {
        ComponentName cm = new ComponentName(getActivity(), NetworkService.class);
        JobInfo info = new JobInfo.Builder(123, cm).setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true).setPeriodic(60 * 1000 * 15).build();

        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            // Log.d(TAG, "Job scheduled");
        } else {
            // Log.d(TAG, "Job scheduling failed");
        }
    }

    public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d("TAGTAGTAG", "Job cancelled");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        container.removeAllViews();
        return view;

    }
}
