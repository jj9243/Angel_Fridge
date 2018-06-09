package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {

    private int index = 0;
    TextView textView;
    Button button;
    String[] text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        textView = findViewById(R.id.guideText);

        text = new String[]{"안녕하세요 냉장고의 요정입니다!", "품목 추가 방식과 메뉴에요\n", "음성인식과 달력을 이용해\n쉽게 입력하세요"
                , "채소, 과일, 고기 등의\n유통기한은 제공해줘요", "홈에서 카드를 터치해서\n품목을 확인하고 수정하세요", "귀여운 슈다와 함께\n냉장고를 관리하세요", "어플을 시작하면 세팅을 맞춰주세요!!!\n"};

        textView.setText(text[0]);
        findViewById(R.id.guideBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "viewPager : " + index);

                if (index != 0)
                    index--;
                update(viewPager, index, 0);
            }
        });

        button = findViewById(R.id.guideBtn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "viewPager : " + index);

                if (index == 6 && button.getText().toString().equals("설정창으로 이동")) {
                    SharedPreferences prefs =getSharedPreferences("first", Activity.MODE_PRIVATE);
                    //boolean test = prefs.getBoolean("first", true);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("check", true);
                    editor.commit();
                    //Log.d("test","먹니?"+test);
                    finish();
                } else if (index < 6) {
                    index++;
                    update(viewPager, index, 1);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()

        {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              //  Log.d("asd", "" + position);
            }

            @Override
            public void onPageSelected(int position) {
               // Log.d("asdasdasd", "" + position);
                index = position;
                update(viewPager, index, 2);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void update(ViewPager v, int index, int flag) {

        textView.setText(text[index]);
        if (index == 6)
            button.setText("설정창으로 이동");
        else
            button.setText("다음");
        v.setCurrentItem(index);

    }
}
