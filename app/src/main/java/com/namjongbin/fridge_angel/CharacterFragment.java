package com.namjongbin.fridge_angel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
    int year,month,day;
    int expiredCount = 0;
    Boolean touchflag=false;
    // TODO: Rename and change types of parameters


    public CharacterFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_character,container,false);
        container.removeAllViews();
        healthy=100;
        ImageView image =(ImageView)rootView.findViewById(R.id.imageView);
        ProgressBar progress=rootView.findViewById(R.id.progressBar);

        int expireCount = this.getExpiredCount(getContext());
        if(expireCount <= 2){
            // 슈다 기분 좋음
        }
        else if(expireCount > 2 && expireCount <= 6){
            // 슈다 기분 보통
        }
        else{
            // 슈다 화남
        }

        final GlideDrawableImageViewTarget cd=new GlideDrawableImageViewTarget(image);

        if(healthy>85) {
            Glide.with(this).load(R.drawable.cdhappy).into(cd);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext()).load(R.drawable.cdtouch).into(cd);
            }
        });

//
//        if(progress !=null){
//            progress.setVisibility(View.VISIBLE);
//            progress.setIndeterminate(true);
//            progress.getIndeterminateDrawable().setColorFilter(Color.rgb(10,180,190), PorterDuff.Mode.MULTIPLY);
//        }

        //http://m.todayhumor.co.kr/view.php?table=programmer&no=13496
        //progress.getProgressDrawable().setColorFilter(Color.rgb(10,210,200), PorterDuff.Mode.MULTIPLY);
        return rootView;
    }

    public int getExpiredCount(Context context){
        int count = 0;
        Calendar cal = Calendar.getInstance();
        int yearToday = cal.get(cal.YEAR);
        int monthToday = cal.get(cal.MONTH) + 1;
        int dayToday = cal.get(cal.DATE);
        final DBHelper db = new DBHelper(context,"ITEM.db",null,2);
        String[] foodItem = db.getResult().split("\n");
        String[] Title = new String[db.columnNum()];
        String[] Context = new String[db.columnNum()];
        for(int i = 0 ; i < db.columnNum(); i++) {
            String[] str = new String[2];
            str = foodItem[i].split(":");
            Title[i] = str[0].replaceAll("[0-9]", "");
            Context[i] = str[1];
            parseItem(Context[i]);

            if(year < yearToday)
                count++;
            else if(month < monthToday)
                count++;
            else if(day < dayToday)
                count++;

        }

        return count;
    }
    public void parseItem(String item){

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy년MM월dd일");
        String d = date.format(today).toString();

        if(item.contains("년")) {
            year = Integer.parseInt(item.substring(item.indexOf('년') - 4, item.indexOf('년')));

        }
        else
            year = Integer.parseInt(d.substring(d.indexOf('년')-4,d.indexOf('년')));
        if(item.contains("월"))
            month = Integer.parseInt(item.substring(item.indexOf('월')-2,item.indexOf('월')).trim());
        else
            month = Integer.parseInt(d.substring(d.indexOf('월')-2,d.indexOf('월')));
        if(item.contains("일"))
            day = Integer.parseInt(item.substring(item.indexOf('일')-2,item.indexOf('일')).trim());
        else
            day = Integer.parseInt(d.substring(d.indexOf('일')-2,d.indexOf('일')));

    }


}
