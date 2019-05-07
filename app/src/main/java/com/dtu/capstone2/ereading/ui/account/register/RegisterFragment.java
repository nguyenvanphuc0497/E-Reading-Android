package com.dtu.capstone2.ereading.ui.account.register;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
import com.dtu.capstone2.ereading.network.utils.ApiExceptionResponse;
import com.dtu.capstone2.ereading.ui.model.AccountRegisterErrorResponse;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 4/1/19
 */
public class RegisterFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private RegisterViewModel viewModel;

    private Button btnContinue;
    private EditText edtUserName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;
    private TextInputLayout layoutUserName;
    private TextInputLayout layoutEmail;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutPasswordConfirm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new RegisterViewModel(new EReadingRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        btnContinue = view.findViewById(R.id.btnRegisterContinue);
        edtUserName = view.findViewById(R.id.edtRegisterUserName);
        edtEmail = view.findViewById(R.id.edtRegisterEmail);
        edtPassword = view.findViewById(R.id.edtRegisterPassword);
        edtPasswordConfirm = view.findViewById(R.id.edtRegisterPasswordConfirm);
        layoutUserName = view.findViewById(R.id.layoutRegisterUserName);
        layoutEmail = view.findViewById(R.id.layoutRegisterEmail);
        layoutPassword = view.findViewById(R.id.layoutRegisterPassword);
        layoutPasswordConfirm = view.findViewById(R.id.layoutRegisterPasswordConfirm);

        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                resetErrorInputLayout();
                AccountRegisterRequest account = new AccountRegisterRequest(edtUserName.getText().toString().trim(),
                        edtPassword.getText().toString().trim(),
                        edtPasswordConfirm.getText().toString().trim(),
                        edtEmail.getText().toString().trim());

                viewModel.createNewAccount(account).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<AccountRegisterRequest>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onSuccess(AccountRegisterRequest accountRegisterRequest) {
                                showSuccessDialog(TAG);
//                                TODO : Xử lí khi đăng kí thành công
                            }

                            @Override
                            public void onError(Throwable e) {
                                dismissLoadingDialog();
                                ApiExceptionResponse response = ((ApiExceptionResponse) e);
                                if (response.getStatusCode() != null && response.getStatusCode() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                                    try {
                                        Gson gson = new Gson();
                                        AccountRegisterErrorResponse registerError = gson.fromJson(response.getMessageError(), AccountRegisterErrorResponse.class);

                                        layoutUserName.setError(registerError.getUserNameError());
                                        layoutEmail.setError(registerError.getEmailError());
                                        layoutPassword.setError(registerError.getPasswordError());
                                        layoutPasswordConfirm.setError(registerError.getPasswordConfirmError());
                                    } catch (Exception ex) {
                                        Log.e(TAG, ex.getMessage());
                                    }
                                } else {
                                    showApiErrorDialog();
                                }
                            }
                        });
            }
        });
    }

    private void resetErrorInputLayout() {
        layoutUserName.setError(null);
        layoutEmail.setError(null);
        layoutPassword.setError(null);
        layoutPasswordConfirm.setError(null);
    }
}
