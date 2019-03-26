package com.dtu.capstone2.ereadingandroid.ui;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.dtu.capstone2.ereadingandroid.R;

public class EnterTextActivity extends AppCompatActivity {
    private EditText edit_text;
    private Button btn_translate;
    private TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_text);
        Intent intent =this.getIntent();
        connectView();
    }

    private void connectView() {
        tvResult = findViewById(R.id.tvResult);
        edit_text = findViewById(R.id.edit_text);
        btn_translate = findViewById(R.id.btn_translate);
        btn_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText(edit_text.getText());
            }
        });
    }
}
