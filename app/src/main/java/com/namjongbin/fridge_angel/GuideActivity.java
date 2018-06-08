package com.namjongbin.fridge_angel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        textView=findViewById(R.id.guideText);

        findViewById(R.id.guideBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "viewPager : " + index);
                if(index != 0){
                    index = index-1;
                    viewPager.setCurrentItem(index);
                    if(index==0){
                          textView.setText("왜 돌아오셨죠?\n슈다가 지켜 보고 있어요");
                    }
                    else if(index==1){
                        textView.setText("품목 추가 방식과 메뉴에요\n");
                    }
                    else if(index==2){
                        textView.setText("음성인식과 달력을 이용해\n쉽게 입력하세요");
                    }
                    else if(index==3){
                        textView.setText("채소, 과일, 고기 등의\n유통기한은 제공해줘요");
                    }
                    else if(index==4){
                        textView.setText("홈에서 카드를 터치해서\n품목을 확인하고 수정하세요");
                    }
                    else if(index==5){
                        textView.setText("귀여운 슈다와 함께\n냉장고를 관리하세요");
                    }
                }

            }
        });

        button=findViewById(R.id.guideBtn2);
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "viewPager : " + index);
                if(index != 5){
                    index = index+1;
                    if(index==1){
                        textView.setText("품목 추가 방식과 메뉴에요\n");
                    }
                    else if(index==2){
                        textView.setText("음성인식과 달력을 이용해\n쉽게 입력하세요");
                    }
                    else if(index==3){
                        textView.setText("채소, 과일, 고기 등의\n유통기한은 제공해줘요");
                    }
                    else if(index==4){
                        textView.setText("홈에서 카드를 터치해서\n품목을 확인하고 수정하세요");
                    }
                    else if(index==5){
                        textView.setText("귀여운 슈다와 함께\n냉장고를 관리하세요");
                        button.setText("종료");
                    }
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
