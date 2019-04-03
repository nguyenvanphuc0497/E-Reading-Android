package com.dtu.capstone2.ereading.ui.account.register;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
import com.dtu.capstone2.ereading.network.utils.ApiException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 4/1/19
 */
public class FragmentRegister extends Fragment {
    private RegisterViewModel viewModel;

    private Button btnContinue;
    private EditText edtUserName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new RegisterViewModel(new EReadingRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_regester, container, false);

        btnContinue = view.findViewById(R.id.btnRegisterContinue);
        edtUserName = view.findViewById(R.id.edtRegisterUserName);
        edtEmail = view.findViewById(R.id.edtRegisterEmail);
        edtPassword = view.findViewById(R.id.edtRegisterPassword);
        edtPasswordConfirm = view.findViewById(R.id.edtRegisterPasswordConfirm);

        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountRegisterRequest account = new AccountRegisterRequest(edtUserName.getText().toString().trim(),
                        edtPassword.getText().toString().trim(),
                        edtPasswordConfirm.getText().toString().trim(),
                        edtEmail.getText().toString().trim());

                viewModel.createNewAccount(account).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<AccountRegisterRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AccountRegisterRequest accountRegisterRequest) {
                        Log.e("xxx", "x" + accountRegisterRequest.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("xxx", "xxxx" + ((ApiException) e).getResponseBody());
                    }
                });
            }
        });
    }
}
