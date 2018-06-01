package com.namjongbin.fridge_angel;

//import android.app.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HomeFragment extends Fragment {
    TextView itemText,ddayText,dateText;
    TableLayout table;
    String Title="" ;
    String Context="";
    int dday,count = 0;
    int year, month, day;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = (View) inflater.inflate(R.layout.fragment_home, container, false);

        container.removeAllViews();

        table= (TableLayout) rootView.findViewById(R.id.table);
        Button youtubeButton = (Button)rootView.findViewById(R.id.recipeBtn);
        Button eatButton=rootView.findViewById(R.id.eatBtn);

        itemText = (TextView)rootView.findViewById(R.id.item);
        dateText = (TextView)rootView.findViewById(R.id.date);
        ddayText = (TextView)rootView.findViewById(R.id.dday);

        parseDate();
        if(dday==0) {
            ddayText.setText("D-DAY");
            table.setBackgroundResource(R.drawable.cardr);
        }
        else if(dday<0){
            table.setBackgroundResource(R.drawable.cardg);
        }
        else
        ddayText.setText("D-"+ dday);
        itemText.setText(Title);
        dateText.setText(Context);

        youtubeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String fname=itemText.getText().toString()+" "+"레시피";

                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");

                intent.putExtra("query", fname);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

        eatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eatFood();
                Toast.makeText(getActivity(),"Eat: "+Title,Toast.LENGTH_LONG).show();
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity().getApplicationContext(),ListActivity.class);
               startActivity(intent);

            }
        });

//        ImageView image =(ImageView)rootView.findViewById(R.id.imageView);
//
//        GlideDrawableImageViewTarget charimg=new GlideDrawableImageViewTarget(image);
//        Glide.with(this).load(R.drawable.choudacry).into(charimg);

        return rootView;
    }
    public void parseDate(){
        final DBHelper db = new DBHelper(getActivity(),"ITEM.db",null,2);
        if(db.getResult().equals("") || db.getResult() == null)
            return;
        String[] foodItem = db.getResult().split("\n");
        String[] str = new String[2];
        for(int i = 0 ; i < 1; i++) {
            String temp="";
            str = foodItem[i].split(":");
            Title = str[0].replaceAll("[0-9]", "");
            Context = str[1];
        }

        int year = Integer.parseInt(Context.substring(Context.indexOf("년")-4,Context.indexOf("년")));
        int month = Integer.parseInt(Context.substring(Context.indexOf("월")-2,Context.indexOf("월")).trim());
        int day = Integer.parseInt(Context.substring(Context.indexOf("일")-2,Context.indexOf("일")).trim());

         dday = countdday(year,month,day);
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

    public void eatFood(){
        final DBHelper db = new DBHelper(getActivity(),"ITEM.db",null,2);
        if(db.getResult().equals("") || db.getResult() == null)
            return;

        String deleteItem = Title;
        String deleteDate = Context;
        parseItem(deleteDate);
        db.delete(db.getId(deleteItem.trim(),year,month,day));

        Intent intent = new Intent(getActivity(),ListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
