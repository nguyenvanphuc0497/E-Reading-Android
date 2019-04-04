package com.dtu.capstone2.ereading.ui.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class LoginFragment extends BaseFragment {
    LoginViewModel loginviewmodel = new LoginViewModel();
    private String strToken;
    private Integer intIduser;
    private String strUserName;
    private String strPassword;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_login, container, false);

        edtPassword = view.findViewById(R.id.txtPassword);
        edtUsername = view.findViewById(R.id.txtUsername);

        btnLogin = view.findViewById(R.id.btnSignInAccount);
        /*
         * this is function event click button login*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                showLoadingDialog();
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
                                dismissLoadingDialog();
                                showSuccessDialog();
                                setCallBakSuccessDialogDismiss(new Function0<Unit>() {
                                    @Override
                                    public Unit invoke() {
                                        getActivity().finish();
                                        return null;
                                    }
                                });
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissLoadingDialog();
                                showApiErrorDialog();
                            }
                        });
            }
        });

        return view;
    }
}
