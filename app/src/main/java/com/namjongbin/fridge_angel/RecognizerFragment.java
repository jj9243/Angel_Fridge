package com.namjongbin.fridge_angel;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.zagum.speechrecognitionview.RecognitionProgressView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class RecognizerFragment extends Activity{
    Intent intent;
    SpeechRecognizer mRecognizer;
    TextView textView;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    RecognitionProgressView recognitionProgressView;
    String item;
    int failCount = 0;
    int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recognizer);

        //animation set
        int[] colors = {
                ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this, R.color.color2),
                ContextCompat.getColor(this, R.color.color3),
                ContextCompat.getColor(this, R.color.color4),
                ContextCompat.getColor(this, R.color.color5)
        };

        int[] heights = { 20, 24, 18, 23, 16 };

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this); //speech recognizer
      //warning
        recognitionProgressView = (RecognitionProgressView) findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(mRecognizer);
        recognitionProgressView.setRecognitionListener(recognitionListener);
//        mRecognizer.setRecognitionListener(recognitionListener);

//        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
//        });

        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.setCircleRadiusInDp(2);
        recognitionProgressView.setSpacingInDp(2);
        recognitionProgressView.setIdleStateAmplitudeInDp(2);
        recognitionProgressView.setRotationRadiusInDp(10);
        recognitionProgressView.play();

        textView = findViewById(R.id.date);
        ImageButton voice_button = findViewById(R.id.start);
        Button close_btn = findViewById(R.id.end);
        ImageButton calendar_btn=findViewById(R.id.calendar);

        voice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecognizer.startListening(intent);
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO
                );
            }
        }

        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CalendarViewer.class);
                startActivity(intent);
            }
        });

    }

    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float v) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int i) {
            textView.setText("너무 늦게 말하면 오류뜹니다");
            failCount++;
            System.out.println("오류 카운트" + failCount);
            if(failCount == 2){
                Intent intent = new Intent(getApplicationContext(),CalendarViewer.class);
                startActivity(intent);
            }

        }

        @Override
        public void onResults(Bundle bundle) {

            final DBHelper dbHelper = new DBHelper(getApplicationContext(),"ITEM.db",null,2);
            dbHelper.setIndex();
            String newDate="";
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;

            ArrayList<String> mResult = bundle.getStringArrayList(key);

            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            if(rs[0].contains("년") && rs[0].contains("월") && rs[0].contains("일")) {
                parseVoice(rs[0]);
                textView.setText("아이템: " + item + year + "년 " + month + "월 " + day + "일 ");
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("item",item.trim());
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("day",day);
                startActivity(intent);
            }
            else {
                textView.setText("품목 이름과 날짜 형식을 제대로 말해 주세요");
                failCount++;
                System.out.println("오류 카운트" + failCount);
                if(failCount == 2){
                    Intent intent = new Intent(getApplicationContext(),CalendarViewer.class);
                    startActivity(intent);
                }
            }

//            recognitionProgressView.stop();
            //textView.setText(rs[0]);

            /*
            dbHelper.insert(item.trim(),year,month,day);


            System.out.println("**********전체 출력*********** \n"+dbHelper.getResult());
            System.out.println("id 출력"+dbHelper.getId(item.trim(),year,month,day));
            System.out.println("아이템 출력"+dbHelper.getItem(dbHelper.getId(item.trim(),year,month,day)));

            int[] date = new int[3];
            date = dbHelper.getDate(dbHelper.getId(item.trim(),year,month,day));
            System.out.print("유통기한 ");
            for(int i = 0 ; i < 3 ; i++){
                System.out.print(date[i] + " ");
            }
            System.out.println();
            */
        }

        @Override
        public void onPartialResults(Bundle bundle) {
        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
    int num;
    public void parseVoice(String voice){

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy년MM월dd일");
        String d = date.format(today).toString();

        if(voice.contains("년")) {
            year = Integer.parseInt(voice.substring(voice.indexOf('년') - 4, voice.indexOf('년')));
            item = voice.substring(0,voice.indexOf("년")-4);
        }
        else
            year = Integer.parseInt(d.substring(d.indexOf('년')-4,d.indexOf('년')));
        if(voice.contains("월"))
            month = Integer.parseInt(voice.substring(voice.indexOf('월')-2,voice.indexOf('월')).trim());
        else
            month = Integer.parseInt(d.substring(d.indexOf('월')-2,d.indexOf('월')));
        if(voice.contains("일"))
            day = Integer.parseInt(voice.substring(voice.indexOf('일')-2,voice.indexOf('일')).trim());
        else
            day = Integer.parseInt(d.substring(d.indexOf('일')-2,d.indexOf('일')));

    }
}
