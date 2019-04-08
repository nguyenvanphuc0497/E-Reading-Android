package com.dtu.capstone2.ereading.ui.account.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.ui.account.register.FragmentRegister;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends BaseFragment {
    LoginViewModel loginviewmodel = new LoginViewModel();
    private String strToken;
    private Integer intIduser;
    private String strUserName;
    private String strPassword;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnLoginRegister;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        edtPassword = view.findViewById(R.id.tvLoginPassword);
        edtUsername = view.findViewById(R.id.tvLoginUsername);
        btnLogin = view.findViewById(R.id.btnSignInAccount);
        btnLoginRegister = view.findViewById(R.id.btnLoginRegister);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEvent();
    }

    private void initEvent() {
        /*
         * this is function event click button login*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = edtUsername.getText().toString();
                strPassword = edtPassword.getText().toString();
                showLoadingDialog();
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

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.layoutManagerAccountContainerActivity, new FragmentRegister());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
