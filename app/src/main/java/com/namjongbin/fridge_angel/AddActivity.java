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
    Button closeBtn, okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);

        dateditText=findViewById(R.id.addDateEdit);

        closeBtn=findViewById(R.id.cancelBtn);
        okBtn=findViewById(R.id.registBtn);

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
