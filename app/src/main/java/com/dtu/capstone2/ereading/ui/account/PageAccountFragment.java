package com.dtu.capstone2.ereading.ui.account;

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

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import java.util.Arrays;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
public class PageAccountFragment extends BaseFragment {
    private PageAccountViewModel mViewModel;

    AlertDialog.Builder builder;
    private LinearLayout linearLayoutLogin;
    private LinearLayout linearLayoutTrinhDoTiengAnh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new PageAccountViewModel(new EReadingRepository());
        initDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_page_account, container, false);
        linearLayoutLogin = view.findViewById(R.id.llLogin);
        linearLayoutTrinhDoTiengAnh = view.findViewById(R.id.llTrinhDoTiengAnh);
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
        linearLayoutTrinhDoTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                mViewModel.getListLevelFromServer().observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<String>>() {
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
                        showApiErrorDialog();
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
        builder.setSingleChoiceItems(
                arrayNameLevel, // Items list
                -1, // Index of checked item (-1 = no selection)
                new DialogInterface.OnClickListener() // Item click listener
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get the alert dialog selected item's text
                        String selectedItem = Arrays.asList(arrayNameLevel).get(i);

                        // Display the selected item's text on snack bar

                    }
                });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Just dismiss the alert dialog after selection
                // Or do something now
            }
        });

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        // Finally, display the alert dialog
        dialog.show();
    }
}
