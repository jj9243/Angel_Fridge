package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 2018-05-30.
 */

public class WifiDialog extends DialogPreference {
    Context c;
    private static final String FileName="WifiInformation";

    public WifiDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;

    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
                    String macAdd = wifi.getConnectionInfo().getBSSID();
                    String wifiName = wifi.getConnectionInfo().getSSID();
                    Toast.makeText(c, "" + macAdd + " " + wifiName, Toast.LENGTH_LONG).show();
                    setSummary(wifiName);
                    SharedPreferences wifiInfo = c.getSharedPreferences(FileName,c.MODE_PRIVATE);
                    SharedPreferences.Editor editor = wifiInfo.edit();
                    editor.putString("macAdd",macAdd);
                    editor.putString("wifiName",wifiName);
                    editor.commit();

                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                    Toast.makeText(c, "연결된 와이파이가 없습니다. 다시 한번 확인해 주세요", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(c, "네트워크에 연결되지 않았습니다. ", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(c, "취소누름", Toast.LENGTH_LONG).show();
    }
}


