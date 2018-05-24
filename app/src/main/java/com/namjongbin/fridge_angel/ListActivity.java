package com.namjongbin.fridge_angel;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView listview ;
    private ListViewAdapter adapter;
    ImageButton imgBtn;
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

        final ArrayList<String> items = new ArrayList<String>() ;

        //변수 초기화
        adapter = new ListViewAdapter();
        listview = findViewById(R.id.listview);
        imgBtn=findViewById(R.id.deleteBtn);

        //어뎁터 할당
        listview.setAdapter(adapter);


        // test item long click
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        for(int i = 0 ; i < db.columnNum(); i++){
            String[] str = new String[2];
            str = foodItem[i].split(":");
            Title[i] = str[0].replaceAll("[0-9]", "");
            Context[i] = str[1];
            //adapter를 통한 값 전달
            adapter.addVO(false,Title[i],Context[i]);
        }

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();

                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked) ;

                        // listview 선택 초기화.
                        listview.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}
