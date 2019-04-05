package com.dtu.capstone2.ereading.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.DataStringReponse;
import com.dtu.capstone2.ereading.network.request.ListVocabulary;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
public class HomeFragment extends BaseFragment {
    HomeFragmentViewModal homeFragmentViewModal = new HomeFragmentViewModal();
    private List<ListVocabulary> listWord;
    private String strInputText;
    private String strReponseText;
    private EditText edtInputText;
    private  EditText edtReponserText;
    private Button btnTranslate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home,container,false);
        edtInputText= view.findViewById(R.id.edtInputText);
        edtReponserText= view.findViewById(R.id.edtReponseText);
        btnTranslate = view.findViewById(R.id.btnTranslate);
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strInputText = edtInputText.getText().toString();
                showLoadingDialog();
                homeFragmentViewModal.getDataStringReponse(strInputText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DataStringReponse>() {
                            @Override
                            public void accept(DataStringReponse dataStringReponse) throws Exception {
                                dismissLoadingDialog();
                                showSuccessDialog();
                                strReponseText = dataStringReponse.getStringData();
                                edtReponserText.setText(strInputText);
                                listWord = dataStringReponse.getListWord();
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
