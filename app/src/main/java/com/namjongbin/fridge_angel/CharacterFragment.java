package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.felipecsl.gifimageview.library.GifImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CharacterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    int healthy;
    int year, month, day;

    int drawable=R.drawable.cdhappy;

    Boolean touchflag = false;
    // TODO: Rename and change types of parameters


    public CharacterFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_character, container, false);
        container.removeAllViews();
        healthy = 100;
        ImageView image = (ImageView) rootView.findViewById(R.id.imageView);

        //character
        ProgressBar progress = rootView.findViewById(R.id.progressBar);

        final GlideDrawableImageViewTarget cd = new GlideDrawableImageViewTarget(image);

        int expireCount = this.getExpiredCount(getContext());


        if (expireCount <= 1) {
//            if(healthy<100)
//            {
//                healthy+=10;
//            }
//            else if(healthy>=90)
//            {
//                healthy=100;
//            }
            healthy=100;
            drawable=R.drawable.cdhappy;
        } else if (expireCount > 2 && expireCount <= 4) {
            //progress.getProgressDrawable().setColorFilter(Color.RED,PorterDuff.Mode.SRC_IN);
            healthy=65;
            // 슈다 기분 보통
            drawable=R.drawable.cdnorm;
        } else if (expireCount > 4 && expireCount <= 6) {
            // 슈다 기분 보통
            healthy=40;
            drawable=R.drawable.cdangry;
        } else {
            // 슈다 화남
            healthy=10;
            //progress.getProgressDrawable().setColorFilter(Color.RED,PorterDuff.Mode.SRC_IN);
            drawable=R.drawable.cdtired;
        }
        Glide.with(this).load(drawable).into(cd);
        progress.setProgress(healthy);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(healthy>40)
                {
                    Glide.with(getContext()).load(R.drawable.cdtouch).into(cd);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO
                            Glide.with(getContext()).load(drawable).into(cd);
                        }
                    }, 3000);
                }

            }
        });
        return rootView;
    }

    public int getExpiredCount(Context context) {
        int count = 0;
        Calendar cal = Calendar.getInstance();
        int yearToday = cal.get(cal.YEAR);
        int monthToday = cal.get(cal.MONTH) + 1;
        int dayToday = cal.get(cal.DATE);
        final DBHelper db = new DBHelper(context, "ITEM.db", null, 2);
        String[] foodItem = db.getResult().split("\n");
        String[] Title = new String[db.columnNum()];
        String[] Context = new String[db.columnNum()];
        for (int i = 0; i < db.columnNum(); i++) {
            String[] str = new String[2];
            str = foodItem[i].split(":");
            Title[i] = str[0].replaceAll("[0-9]", "");
            Context[i] = str[1];
            parseItem(Context[i]);

            if (year < yearToday)
                count++;
            else if (month < monthToday)
                count++;
            else if (day < dayToday)
                count++;

        }

        return count;
    }

    public void parseItem(String item) {

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy년MM월dd일");
        String d = date.format(today).toString();

        if (item.contains("년")) {
            year = Integer.parseInt(item.substring(item.indexOf('년') - 4, item.indexOf('년')));

        } else
            year = Integer.parseInt(d.substring(d.indexOf('년') - 4, d.indexOf('년')));
        if (item.contains("월"))
            month = Integer.parseInt(item.substring(item.indexOf('월') - 2, item.indexOf('월')).trim());
        else
            month = Integer.parseInt(d.substring(d.indexOf('월') - 2, d.indexOf('월')));
        if (item.contains("일"))
            day = Integer.parseInt(item.substring(item.indexOf('일') - 2, item.indexOf('일')).trim());
        else
            day = Integer.parseInt(d.substring(d.indexOf('일') - 2, d.indexOf('일')));

    }
}