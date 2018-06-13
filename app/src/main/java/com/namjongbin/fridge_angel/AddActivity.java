package com.namjongbin.fridge_angel;
/**
 * @brief   Add new item or Change Item information
 * @details When user add new Item for Calender Interface, AddActivity can be that user insert new item or change item
 * @author Seok bin Im
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddActivity extends Activity {

    TextView dateText;
    EditText itemText;
    Button closeBtn, okBtn;
    String item;
    int year, month, day, id;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        dateText = findViewById(R.id.addDateEdit);
        itemText = (EditText) findViewById(R.id.addItemEdit);

        Intent intent = getIntent();
        item = intent.getStringExtra("item");
        id = intent.getIntExtra("item_id", 0);
        year = intent.getIntExtra("year", 2018);
        month = intent.getIntExtra("month", 6);
        day = intent.getIntExtra("day", 11);
        date = intent.getStringExtra("date");

        itemText.setText(item);
       // Toast.makeText(getApplicationContext(), "" + date, Toast.LENGTH_LONG).show();
        if (date != null)
            dateText.setText(date);
        else
        dateText.setText("" + year + "년 " + month + "월 " + day + "일");

        closeBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.registBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DBHelper db = new DBHelper(getApplicationContext(), "ITEM.db", null, 2);
                item = itemText.getText().toString();
                if (id == 0)
                    db.insert(item.trim(), year, month, day);
                else
                    db.update(id, item.trim(), year, month, day);
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                // new Alarm(getApplicationContext(), 17, 30).Alarm();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        dateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    item = itemText.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), CalendarViewer.class);
                    intent.putExtra("tempItem", item);
                    intent.putExtra("item_id", id);
                    startActivity(intent);
                    //finish();
                }
                return true;
            }
        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onStop() {
        super.onStop();
        finish();
    }
}
