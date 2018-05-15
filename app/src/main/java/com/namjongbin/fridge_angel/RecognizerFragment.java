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
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecognizerFragment extends Activity{
    Intent intent;
    SpeechRecognizer mRecognizer;
    TextView textView;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    String item;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recognizer);

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(recognitionListener);
        textView = (TextView) findViewById(R.id.date);
        Button button = (Button) findViewById(R.id.start);
        Button close_btn = (Button)findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
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

        }

        @Override
        public void onResults(Bundle bundle) {
            final DBHelper dbHelper = new DBHelper(getApplicationContext(),"ITEM.db",null,1);
            String newDate="";
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = bundle.getStringArrayList(key);

            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            item = rs[0].substring(0,rs[0].indexOf("년")-4);
            String d = rs[0].substring(rs[0].indexOf("년")-4);
            //textView.setText(rs[0]);

            try {
                date = new SimpleDateFormat("yyyy년 MM월 dd일").parse(d);
                newDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            }catch(ParseException e){
                textView.setText("날짜 형식을 00년 00월 00일로 말해주세요");
                e.printStackTrace();
            }
            textView.setText(item+" " + newDate);
            dbHelper.insert(item,newDate);
        }

        @Override
        public void onPartialResults(Bundle bundle) {
        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
}
