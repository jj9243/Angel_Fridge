package com.namjongbin.fridge_angel;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    private ListView listview ;
    private ListViewAdapter adapter;
    private int[] img = {R.drawable.sd_eat ,R.drawable.sd_tired,R.drawable.sd_angry};

    int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final DBHelper db = new DBHelper(getApplicationContext(),"ITEM.db",null,2);
        db.columnNum();
        String[] Title = new String[db.columnNum()];
        String[] Context = new String[db.columnNum()];
        System.out.println("************************************결과 출력***********************\n"+db.getResult());
        String[] foodItem = db.getResult().split("\n");

        //변수 초기화
        adapter = new ListViewAdapter();
        listview = (ListView) findViewById(R.id.listView);

        //어뎁터 할당
        listview.setAdapter(adapter);

        for(int i = 0 ; i < db.columnNum(); i++){
            String[] str = new String[2];
            str = foodItem[i].split(":");
            Title[i] = str[0].replaceAll("[0-9]", "");
            Context[i] = str[1];
            //adapter를 통한 값 전달
            adapter.addVO(ContextCompat.getDrawable(this,img[i]),Title[i],Context[i]);
        }


    }
}
