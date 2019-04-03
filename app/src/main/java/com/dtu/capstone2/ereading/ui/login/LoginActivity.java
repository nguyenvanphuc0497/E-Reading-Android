package com.dtu.capstone2.ereading.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.ui.home.HomeActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginviewmodel = new LoginViewModel();
    private String strToken;
    private Integer intIduser;
    private String strUserName;
    private String strPassword;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    final Context context = this;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        edtPassword = findViewById(R.id.txtPassword);
        edtUsername = findViewById(R.id.txtUsername);

        btnLogin = findViewById(R.id.btnSignInAccount);
        /*
         * this is function event click button login*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = edtUsername.getText().toString();
                strPassword = edtPassword.getText().toString();

                loginviewmodel.GetDataLoginRequest(new AccountLoginRequest(strUserName, strPassword))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DataLoginRequest>() {
                            @Override
                            public void accept(DataLoginRequest dataLoginRequest) throws Exception {
                                strToken = dataLoginRequest.getStringToken();
                                intIduser = dataLoginRequest.getIntId();
                                final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "",
                                        "Loading. Please wait...", true);
                                final Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                    }
                                };
                                Handler handler = new Handler();
                                handler.postDelayed(runnable, 2000);
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                                dialog.setCancelable(false);
                                dialog.setTitle("Lỗi");
                                dialog.setMessage("Tên đăng nhập hoặc mật khẩu không chính xác");
                                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Action for "Delete".
                                    }
                                });

                                final AlertDialog alert = dialog.create();
                                alert.show();
//
                            }
                        });

            }
        });


    }
}

