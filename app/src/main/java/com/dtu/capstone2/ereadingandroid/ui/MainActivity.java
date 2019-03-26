package com.dtu.capstone2.ereadingandroid.ui;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.dtu.capstone2.ereadingandroid.R;

public class MainActivity extends AppCompatActivity  {
    Button buttonGoToEnterText;
    @SuppressLint("CheckResult")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGoToEnterText = findViewById(R.id.btn_go_to);
        buttonGoToEnterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EnterTextActivity.class);
                startActivity(intent);
            }
        });
    }
}
