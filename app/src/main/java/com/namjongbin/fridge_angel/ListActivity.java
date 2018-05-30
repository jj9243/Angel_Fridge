package com.namjongbin.fridge_angel;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {
    private ListView listview ;
    private ListViewAdapter adapter = new ListViewAdapter();
    ImageButton imgBtn;
    Button deleteBtn;
    Boolean opendelete =false;
    private int[] img = {R.drawable.sd_eat ,R.drawable.sd_tired,R.drawable.sd_angry};

    int checked,year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }
    @Override
    public void onResume(){
        super.onResume();
        final DBHelper db = new DBHelper(getApplicationContext(),"ITEM.db",null,2);

        db.columnNum();
        String[] Title = new String[db.columnNum()];
        String[] Context = new String[db.columnNum()];
        System.out.println("************************************결과 출력***********************\n"+db.getResult());
        String[] foodItem = db.getResult().split("\n");

        final ArrayList<String> items = new ArrayList<String>() ;

        //변수 초기화
        //adapter = new ListViewAdapter();
        listview = findViewById(R.id.listview);
        imgBtn=findViewById(R.id.deleteBtn);
        deleteBtn=findViewById(R.id.popDeleteBtn);

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
                if(!opendelete) {
                    adapter.toggleCheckBox(true);
                    deleteBtn.setVisibility(View.VISIBLE);
                    opendelete =true;
                }else{
                    adapter.toggleCheckBox(false);
                    deleteBtn.setVisibility(View.GONE);
                    opendelete =false;
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int count ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        for(int i = 0 ; i < adapter.getCheckArr().size();i++) {
                            ListVO temp = (ListVO) adapter.getItem(adapter.getCheckArr().get(i));
                            String deleteItem = temp.getTitle();
                            String deleteDate = temp.getContext();
                            parseItem(deleteDate);
                            db.delete(db.getId(deleteItem.trim(),year,month,day));
                            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        // listview 선택 초기화.
                        listview.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();

                    }
                }

            }
        });

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
