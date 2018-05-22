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
                final DBHelper db = new DBHelper(getApplicationContext(),"ITEM.db",null,2);
                item = itemText.getText().toString();
                db.insert(item.trim(),year,month,day);
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
