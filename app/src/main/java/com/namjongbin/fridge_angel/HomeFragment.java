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
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HomeFragment extends Fragment {
    TextView itemText,ddayText,dateText;
    TableLayout table;
    String Title="" ;
    String Context="";
    int dday,count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = (View) inflater.inflate(R.layout.fragment_home, container, false);

        container.removeAllViews();

        table= (TableLayout) rootView.findViewById(R.id.table);
        Button youtubeButton = (Button)rootView.findViewById(R.id.recipeBtn);

        itemText = (TextView)rootView.findViewById(R.id.item);
        dateText = (TextView)rootView.findViewById(R.id.date);
        ddayText = (TextView)rootView.findViewById(R.id.dday);

        parseDate();
        if(dday==0) {
            ddayText.setText("D-DAY");
            table.setBackgroundResource(R.drawable.cardg);
        }
        else if(dday<0){
            table.setBackgroundResource(R.drawable.cardr);
        }
        else
        ddayText.setText("D-"+ dday);
        itemText.setText(Title);
        dateText.setText(Context);

        youtubeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");

                intent.putExtra("query", "최유정 직캠");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity().getApplicationContext(),ListActivity.class);
               startActivity(intent);

            }
        });
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
}
