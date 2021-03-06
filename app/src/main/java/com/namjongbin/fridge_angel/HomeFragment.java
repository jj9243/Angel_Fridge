package com.namjongbin.fridge_angel;

/**
 * @brief  Control all of Home(first screen to user)
 * @details check application First run, Set Card view(one of Item) and Character
 * @author Jong keon Kim, Seok bin Im, Kang woo Nam
 */

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.style.ForegroundColorSpan;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HomeFragment extends Fragment {
    TextView itemText, ddayText, dateText;
    ImageView character;
    Button eatButton;
    TableLayout table;
    String Title = "";
    String Context = "";
    int dday, count = 0;
    int year, month, day;
    Calendar calendar = Calendar.getInstance();
    int y = -1;
    int m = -1;
    int d = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = (View) inflater.inflate(R.layout.fragment_home, container, false);

        container.removeAllViews();

        SharedPreferences prefs = rootView.getContext().getSharedPreferences("first", Activity.MODE_PRIVATE);

        boolean first = prefs.getBoolean("first", true);

        Log.i("First?", "ready");
        if (first) {
            Log.i("First?", "first");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first", false);
            editor.commit();

            Intent intent = new Intent(getActivity().getApplicationContext(), GuideActivity.class);
            startActivity(intent);
            //prefs.edit().putBoolean("First",false).apply();
        } else {
            Log.i("First?", "no");
        }

        table = (TableLayout) rootView.findViewById(R.id.table);
        Button youtubeButton = (Button) rootView.findViewById(R.id.recipeBtn);
        eatButton = rootView.findViewById(R.id.eatBtn);

        character = rootView.findViewById(R.id.imageChar);
        final GlideDrawableImageViewTarget charimg = new GlideDrawableImageViewTarget(character);


        itemText = (TextView) rootView.findViewById(R.id.item);
        dateText = (TextView) rootView.findViewById(R.id.date);
        ddayText = (TextView) rootView.findViewById(R.id.dday);

        parseDate();
        if (dday < 0) {
            ddayText.setText("D+" + -dday);
            table.setBackgroundResource(R.drawable.cardr);
            eatButton.setText("버렸어요 :(");
        } else if (dday == 0) {
            ddayText.setText("D-DAY");
        } else {
            ddayText.setText("D-" + dday);
        }

        final DBHelper db = new DBHelper(getActivity(), "ITEM.db", null, 2);
        if(db.columnNum()==0) {
            itemText.setText("냉장고가 비어 있습니다");
            itemText.setTextSize(24);
            dateText.setText("");
            ddayText.setText("");
            table.setBackgroundResource(R.drawable.card);
            eatButton.setText("비었어요 :)");
        }
        else {
            itemText.setText(Title);
            dateText.setText(Context + " 까지");
        }
        youtubeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(db.columnNum()==0)
                {
                    Toast.makeText(getContext(),"음식이 없어요",Toast.LENGTH_LONG).show();
                }
                else {
                String fname = itemText.getText().toString() + " " + "레시피";

                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");

                intent.putExtra("query", fname);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
                }
            }
        });

        eatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.columnNum()==0)
                {
                    Toast.makeText(getContext(),"음식이 없어요",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), Title+" 냠냠", Toast.LENGTH_LONG).show();
                    eatFood();
                    Glide.with(rootView.getContext()).load(R.drawable.cdeatw).into(charimg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO
                            Glide.with(rootView.getContext()).load(R.drawable.cdw).into(charimg);
                        }
                    }, 2000);

                }
                if(db.columnNum()==0) {
                    itemText.setText("냉장고가 비어 있습니다");
                    itemText.setTextSize(24);
                    dateText.setText("");
                    ddayText.setText("");
                    table.setBackgroundResource(R.drawable.card);
                    eatButton.setText("비었어요 :)");
                }
            }
        });

        character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment2 = new CharacterFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_fragment, fragment2);
                fragmentTransaction.commit();
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ListActivity.class);
                startActivity(intent);

            }
        });

        Glide.with(this).load(R.drawable.cdw).into(charimg);

        return rootView;
    }

    public void parseDate() {
        final DBHelper db = new DBHelper(getActivity(), "ITEM.db", null, 2);
        if (db.getResult().equals("") || db.getResult() == null) {
            return;
        }

        String[] foodItem = db.getResult().split("\n");
        String[] str = new String[2];
        System.out.println("*******************푸드어이템" + foodItem[0]);
        for (int i = 0; i < 1; i++) {
            String temp = "";
            str = foodItem[i].split(":");
            Title = str[0].replaceAll("[0-9]", "");
            Context = str[1];

        }


        int year = Integer.parseInt(Context.substring(Context.indexOf("년") - 4, Context.indexOf("년")));
        int month = Integer.parseInt(Context.substring(Context.indexOf("월") - 2, Context.indexOf("월")).trim());
        int day = Integer.parseInt(Context.substring(Context.indexOf("일") - 2, Context.indexOf("일")).trim());

        dday = countdday(year, month, day);
    }

    public int countdday(int myear, int mmonth, int mday) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

            mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear, mmonth, mday);// D-day의 날짜를 입력
            Log.e("테스트", simpleDateFormat.format(todaCal.getTime()) + "");
            Log.e("테스트", simpleDateFormat.format(ddayCal.getTime()) + "");

            long today = todaCal.getTimeInMillis() / 86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long dday = ddayCal.getTimeInMillis() / 86400000;
            long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void eatFood() {
        final DBHelper db = new DBHelper(getActivity(), "ITEM.db", null, 2);

            String deleteItem = itemText.getText().toString();
            String deleteDate = dateText.getText().toString();
            parseItem(deleteDate);
            db.delete(db.getId(deleteItem.trim(), year, month, day));
            parseDate();
            if (db.getResult().equals("") || db.getResult() == null) {
                ddayText.setText("D-DAY");
                itemText.setText("");
                dateText.setText("까지");
            } else {
                if (dday < 0) {
                    ddayText.setText("D+" + -dday);
                    table.setBackgroundResource(R.drawable.cardr);
                } else if (dday == 0) {
                    ddayText.setText("D-DAY");
                } else {
                    ddayText.setText("D-" + dday);
                }
                itemText.setText(Title);
                dateText.setText(Context + " 까지");
            }
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
