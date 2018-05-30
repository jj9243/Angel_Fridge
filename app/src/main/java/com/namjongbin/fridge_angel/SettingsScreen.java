package com.namjongbin.fridge_angel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.InetAddress;

public class SettingsScreen extends PreferenceFragment {

    EditTextPreference user;
    SwitchPreference notyOnOff;
    ListPreference notyLocation;
SharedPreferences wifiInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);

        user = (EditTextPreference) findPreference("user");
        notyOnOff = (SwitchPreference) findPreference("switch");
        notyLocation = (ListPreference) findPreference("location");
        WifiDialog wifiSetting =(WifiDialog)findPreference("wifi");

        wifiInfo=getActivity().getSharedPreferences("WifiInformation",getActivity().MODE_PRIVATE);

        if (user.getText() != null && notyLocation.getEntry() != null) {
            user.setSummary(user.getText().toString());
            notyLocation.setSummary(notyLocation.getEntry());
        }

            Log.d("tt","  "+wifiSetting.getSummary());
            wifiSetting.setSummary(wifiInfo.getString("wifiName",""));
            Log.d("tt","asdad");

            Log.d("tt","zzzzz");

        user.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String name = newValue.toString();
                preference.setSummary(name);
                return true;
            }
        });


        notyLocation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String where = newValue.toString();
                preference.setSummary(where);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        container.removeAllViews();
        return view;

    }
}
