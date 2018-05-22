package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {

    EditText dateditText;
    EditText itemText;
    Button closeBtn, okBtn;
    String item;
    int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);

        dateditText=findViewById(R.id.addDateEdit);
        itemText = (EditText)findViewById(R.id.addItemEdit);

        Intent intent = getIntent();
        item = intent.getStringExtra("item");
        year = intent.getIntExtra("year",2018);
        month = intent.getIntExtra("month",6);
        day = intent.getIntExtra("day",1);
        itemText.setText(item);
        dateditText.setText(year +"년 "+month +"월 "+day+"일");

        closeBtn=findViewById(R.id.cancelBtn);
        okBtn=findViewById(R.id.registBtn);

        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final DBHelper dbHelper = new DBHelper(getApplicationContext(),"ITEM.db",null,2);
                //dbHelper.insert(item.trim(),year,month,day);
                dbHelper.delete(10);
                dbHelper.delete(11);
                dbHelper.delete(12);
                dbHelper.delete(13);
                dbHelper.delete(14);
                dbHelper.delete(15);
                dbHelper.delete(16);
                dbHelper.delete(17);
                dbHelper.delete(18);
                dbHelper.delete(19);
                /*
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
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent);

            }
        });

        dateditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CalendarViewer.class);
                startActivity(intent);
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
