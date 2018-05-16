package com.namjongbin.fridge_angel;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    private ListView listview ;
    private ListViewAdapter adapter;
    private int[] img = {R.drawable.tt ,R.drawable.tom,R.drawable.wonkim};
    private String[] Title = {"타노스","스파이더맨","김원교수님"};
    private String[] Context = {"외계인","피터파커","원킴"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //변수 초기화
        adapter = new ListViewAdapter();
        listview = (ListView) findViewById(R.id.listView);

        //어뎁터 할당
        listview.setAdapter(adapter);

        //adapter를 통한 값 전달
        for(int i=0; i<img.length;i++){
            adapter.addVO(ContextCompat.getDrawable(this,img[i]),Title[i],Context[i]);
        }
    }
}
