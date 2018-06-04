package com.namjongbin.fridge_angel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
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

public class CharacterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int dday;
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

        final DBHelper db = new DBHelper(getActivity(),"ITEM.db",null,2);

        ImageView image =(ImageView)rootView.findViewById(R.id.imageView);
        ProgressBar progress=rootView.findViewById(R.id.progressBar);

        final GlideDrawableImageViewTarget cd=new GlideDrawableImageViewTarget(image);

//        if(db.) {
//            Glide.with(this).load(R.drawable.cdhappy).into(cd);
//        }

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

    public int countdday(int myear, int mmonth, int mday) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

            mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear,mmonth,mday);// D-day의 날짜를 입력
            Log.e("테스트",simpleDateFormat.format(todaCal.getTime()) + "");
            Log.e("테스트",simpleDateFormat.format(ddayCal.getTime()) + "");

            long today = todaCal.getTimeInMillis()/86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long dday = ddayCal.getTimeInMillis()/86400000;
            long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
