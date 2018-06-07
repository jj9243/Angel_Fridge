package com.namjongbin.fridge_angel;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GuideActivity extends AppCompatActivity {

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "viewPager : " + index);
                if(index != 0){
                    index = index-1;
                    viewPager.setCurrentItem(index);
                }

            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "viewPager : " + index);
                if(index != 5){
                    index = index+1;
                    viewPager.setCurrentItem(index);
                }
                else if(index==5)
                {
                    finish();
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {index = position;}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
