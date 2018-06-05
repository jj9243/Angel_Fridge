package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class NavBarActivity extends PreferenceActivity {

    ImageView profileImg;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        profileImg=findViewById(R.id.profileImg);

//        userName=findViewById(R.id.userNameView);
//
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String name=sharedPreferences.getString("user","");
//        if(userName!=null) {
//            Log.d("aaa", "zzz");
//            userName.setText(name);
//        }
//        else
//            Log.d("aaa","wwww");
    }
}
