package com.namjongbin.fridge_angel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsScreen extends PreferenceFragment{

    SharedPreferences spre;
    EditTextPreference user;
    SwitchPreference notyOnOff;
    ListPreference notyLocation;

    String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);
//
//        user=(EditTextPreference)findPreference("user");
//
//        notyOnOff=(SwitchPreference)findPreference("switch");
//        notyOnOff.setEnabled(true);
//
//        notyLocation=(ListPreference)findPreference("location");
//
//        if(!spre.getString("location", "").equals("")){
//            notyLocation.setSummary(spre.getString("location","AnyWhere"));
//        }

    }

    SharedPreferences.OnSharedPreferenceChangeListener spreListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            if(key.equals("user")){
//
//            }

            if(key.equals("location")){
                notyLocation.setSummary(spre.getString("location", "AnyWhere"));
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        container.removeAllViews();
        return view;


    }
}
