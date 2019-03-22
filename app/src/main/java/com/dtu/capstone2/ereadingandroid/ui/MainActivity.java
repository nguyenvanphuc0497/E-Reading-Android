package com.dtu.capstone2.ereadingandroid.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.dtu.capstone2.ereadingandroid.R;
import com.dtu.capstone2.ereadingandroid.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereadingandroid.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereadingandroid.network.response.Token;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    private EReadingRepository localRepository = new EReadingRepository();
    private String tk = "";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.enter_text);
        editText = findViewById(R.id.edittext);
        //editText.setText(); //thiết lập nội dung
      // editText.getText(); //lấy nội dung

        localRepository.login(new AccountLoginRequest("admin", "admin123456"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Token>() {
                    @Override
                    public void accept(Token token) throws Exception {
                        tk = token.getToken();
                        Log.e("xxx", "accept: " + token);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
