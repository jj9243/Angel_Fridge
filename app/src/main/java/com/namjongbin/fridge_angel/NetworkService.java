package com.namjongbin.fridge_angel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @brief  Checking Network
 * @details Stored in SharedPreference Networking Data. Check Network between set Network and new network
 * @author Kang woo Nam
 */

public class NetworkService extends JobService {

    private static final String TAG = "TagTagTag";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {

               // ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
               // NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                String text = null;

                String add = wifi.getConnectionInfo().getBSSID();
                text = wifi.getConnectionInfo().getSSID();

                SharedPreferences wifiInfo = getApplicationContext().getSharedPreferences("WifiInformation",Context.MODE_PRIVATE);
                //String wifiName=wifiInfo.getString("wifiName","");
                String macAdd=wifiInfo.getString("macAdd","");

                if(add.equals(macAdd))
                {
                    AlarmManager am = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
                    Log.d(TAG, "같아요 같아!");
                    PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 3, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), sender);
                }
                else
                    Log.d(TAG, "달라달라");


                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}