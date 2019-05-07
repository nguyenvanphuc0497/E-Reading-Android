package com.dtu.capstone2.ereading.ui.account.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.network.utils.ApiExceptionResponse;
import com.dtu.capstone2.ereading.ui.account.register.RegisterFragment;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;
import com.dtu.capstone2.ereading.ui.utils.RxBusTransport;
import com.dtu.capstone2.ereading.ui.utils.Transport;
import com.dtu.capstone2.ereading.ui.utils.TypeTransportBus;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends BaseFragment {
    public final String TAG = getClass().getSimpleName();

    LoginViewModel loginviewmodel;
    private String strUserName;
    private String strPassword;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnLoginRegister;
    private TextInputLayout layoutPassword;

    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginviewmodel = new LoginViewModel(new EReadingRepository(), new LocalRepository(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        getManagerSubscribe().add(RxBusTransport.INSTANCE.listen()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Transport>() {
                    @Override
                    public void accept(Transport transport) throws Exception {
                        if (transport.getTypeTransport() == TypeTransportBus.CALL_BACK_DIALOG_SUCCESS_DISMISS) {
                            getActivity().finish();
                        }
                    }
                }));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        edtPassword = view.findViewById(R.id.tvLoginPassword);
        edtUsername = view.findViewById(R.id.tvLoginUsername);
        btnLogin = view.findViewById(R.id.btnSignInAccount);
        btnLoginRegister = view.findViewById(R.id.btnLoginRegister);
        layoutPassword = view.findViewById(R.id.layoutLoginPassword);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEvent();
    }

    private void initEvent() {
        /*
         * this is function typeTransport click button login*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = edtUsername.getText().toString();
                strPassword = edtPassword.getText().toString();
                layoutPassword.setError(null);

                showLoadingDialog();
                getManagerSubscribe().add(loginviewmodel.GetDataLoginRequest(new AccountLoginRequest(strUserName, strPassword))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DataLoginRequest>() {
                            @Override
                            public void accept(DataLoginRequest dataLoginRequest) throws Exception {
                                showSuccessDialog(TAG);
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissLoadingDialog();
                                ApiExceptionResponse response = ((ApiExceptionResponse) throwable);
                                if (response.getStatusCode() != null && response.getStatusCode() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                                    layoutPassword.setError("Tài khoản và mật khẩu nhập vào không chính xác");
                                } else {
                                    showApiErrorDialog();
                                }
                            }
                        }));
            }
        });

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(R.id.layoutManagerAccountContainerActivity, new RegisterFragment(), true);
            }
        });
    }
}
