package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class NavBarActivity extends PreferenceActivity {

    ImageView profileImg;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        profileImg=findViewById(R.id.profileImg);
        username=findViewById(R.id.userNameView);

    }
}
