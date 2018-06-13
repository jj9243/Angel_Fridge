package com.namjongbin.fridge_angel;

/**
 * @brief  navigation Bar customizing
 * @author Jong keon Kim
 */

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * \todo add customizing to user
 *
 */

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
