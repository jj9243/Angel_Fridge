package com.namjongbin.fridge_angel;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ListActivity extends AppCompatActivity {
    TextView currentInfoText,afterInfoText;
    private ListView listview ;
    private String[] testStringArray;
    private ListViewAdapter adapter;
    LinearLayout listItem;
    ImageButton trashBtn,ytBtn;
    ImageView imageView;
    Button deleteBtn;
    Boolean opendelete =false;
    Boolean  animationsLocked;
    int checked,year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final DBHelper db = new DBHelper(getApplicationContext(),"ITEM.db",null,2);

        testStringArray = new String[100];
        for (int i = 0; i < testStringArray.length; i++)
        {
            testStringArray[i] = "test No. = " + i;
        }

        adapter = new ListViewAdapter(this,0,testStringArray);
        db.columnNum();
        String[] Title = new String[db.columnNum()];
        String[] Context = new String[db.columnNum()];
        System.out.println("***********************결과 출력***********************\n"+db.getResult());
        String[] foodItem = db.getResult().split("\n");

        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);

        animation.setDuration(50);

        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(200);

        set.addAnimation(animation);

        LayoutAnimationController controller =
                new LayoutAnimationController(set, 0.7f);

        //변수 초기화
        //adapter = new ListViewAdapter();
        listItem=findViewById(R.id.listItem);
        listview = findViewById(R.id.listview);
        listview.setLayoutAnimation(controller);
        trashBtn =findViewById(R.id.deleteBtn);
        deleteBtn=findViewById(R.id.popDeleteBtn);
        currentInfoText = (TextView)findViewById(R.id.currentInfoText);
        afterInfoText = (TextView)findViewById(R.id.afterInfoText);
        //imageView=findViewById(R.id.corner);
        //어뎁터 할당
        listview.setAdapter(adapter);


        // test item long click
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return true;
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

        trashBtn.setOnClickListener(new View.OnClickListener() {
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

                        //애니메이션
                        // listview 갱신.
                        //adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        currentInfoText.setText("저장된 식품 "+adapter.getCount()+"개");
        afterInfoText.setText("만료된 식품 "+this.getExpiredCount(getApplicationContext())+"개");
    }

    @Override
    public void onResume(){
        super.onResume();


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
    public int getExpiredCount(Context context) {
        int count = 0;
        Calendar cal = Calendar.getInstance();
        int yearToday = cal.get(cal.YEAR);
        int monthToday = cal.get(cal.MONTH) + 1;
        int dayToday = cal.get(cal.DATE);
        final DBHelper db = new DBHelper(context, "ITEM.db", null, 2);
        String[] foodItem = db.getResult().split("\n");
        String[] Title = new String[db.columnNum()];
        String[] Context = new String[db.columnNum()];
        for (int i = 0; i < db.columnNum(); i++) {
            String[] str = new String[2];
            str = foodItem[i].split(":");
            Title[i] = str[0].replaceAll("[0-9]", "");
            Context[i] = str[1];
            parseItem(Context[i]);

            if (year < yearToday)
                count++;
            else if (month < monthToday)
                count++;
            else if (day < dayToday && month == monthToday)
                count++;

        }
        return count;
    }
}
