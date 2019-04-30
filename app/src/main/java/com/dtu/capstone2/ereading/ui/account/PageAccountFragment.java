package com.dtu.capstone2.ereading.ui.account;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository;
import com.dtu.capstone2.ereading.network.utils.ApiExceptionResponse;
import com.dtu.capstone2.ereading.ui.account.login.LoginFragment;
import com.dtu.capstone2.ereading.ui.model.ErrorUnauthorizedRespone;
import com.dtu.capstone2.ereading.ui.model.LevelEnglish;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;
import com.dtu.capstone2.ereading.ui.utils.RxBusTransport;
import com.dtu.capstone2.ereading.ui.utils.Transport;
import com.dtu.capstone2.ereading.ui.utils.TypeTransportBus;
import com.google.gson.Gson;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
public class PageAccountFragment extends BaseFragment {
    private PageAccountViewModel mViewModel;

    AlertDialog.Builder builder;
    private LinearLayout linearLayoutLogin;
    private LinearLayout linearLayoutTrinhDoTiengAnh;
    private LinearLayout linnearLayoutLogout;
    private TextView tvEmailUser;
    private int mItemSelect = -1;

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new PageAccountViewModel(new EReadingRepository(), new LocalRepository(getContext()));
        initDialog();
        RxBusTransport.INSTANCE.listen()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Transport>() {
                    @Override
                    public void accept(Transport transport) throws Exception {
                        if (transport.getTypeTransport() == TypeTransportBus.DIALOG_SUCCESS && transport.getSender().equals(LoginFragment.class.getSimpleName())) {
                            loadDataUserToView();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_page_account, container, false);
        linearLayoutLogin = view.findViewById(R.id.llLogin);
        linnearLayoutLogout = view.findViewById(R.id.layoutLogout);
        linearLayoutTrinhDoTiengAnh = view.findViewById(R.id.llTrinhDoTiengAnh);
        tvEmailUser = view.findViewById(R.id.tv_page_account_manager_email_user);

        loadDataUserToView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ManagerAccountContainerActivity.class));
            }
        });
        linnearLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mViewModel.getEmailFromLocal().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setCancelable(false);
                    dialog.setTitle("Thông báo!");
                    dialog.setMessage("Bạn có muốn đăng xuất?");
                    dialog.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            mViewModel.clearToken();
                            mViewModel.clearEmail();
                            loadDataUserToView();
                        }
                    })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                }
                            });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                } else {
                    Toast.makeText(getContext(),
                            "Bạn chưa đăng nhập",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
        linearLayoutTrinhDoTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                mViewModel.getListLevelFromServer()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new SingleObserver<List<String>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<String> strings) {
                                dismissLoadingDialog();
                                showDialog(strings.toArray(new String[]{}));
                            }

                            @Override
                            public void onError(Throwable e) {
                                ApiExceptionResponse response = ((ApiExceptionResponse) e);
                                if (response.getStatusCode() != null && response.getStatusCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                                    Gson gson = new Gson();
                                    ErrorUnauthorizedRespone dataError = gson.fromJson(response.getMessageError(), ErrorUnauthorizedRespone.class);
                                    showMessageErrorDialog(dataError.getDetail());
                                } else {
                                    showApiErrorDialog();
                                }
                            }
                        });
            }
        });
    }

    private void initDialog() {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Trình độ tiếng anh của bạn ?");
    }

    private void showDialog(final String[] arrayNameLevel) {
        mItemSelect = -1;
        builder.setSingleChoiceItems(arrayNameLevel, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mItemSelect = i;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mItemSelect != -1) {
                    handelEventSetLevelUserToServer(mItemSelect);
                }
                mItemSelect = -1;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadDataUserToView() {
        if (!mViewModel.getEmailFromLocal().equals("") && !mViewModel.getTokenFromLocal().equals("")) {
            tvEmailUser.setText(mViewModel.getEmailFromLocal());
            linearLayoutLogin.setEnabled(false);
            linnearLayoutLogout.setVisibility(View.VISIBLE);
        } else {
            tvEmailUser.setText(getString(R.string.page_account_login_info_default));
            linearLayoutLogin.setEnabled(true);
            linnearLayoutLogout.setVisibility(View.GONE);
        }
    }

    private void handelEventSetLevelUserToServer(int position) {
        showLoadingDialog();
        mViewModel.setLevelOfUserToServer(position)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<LevelEnglish>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(LevelEnglish levelEnglish) {
                        showSuccessDialog("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showApiErrorDialog();
                    }
                });
    }
}
