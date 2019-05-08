package com.dtu.capstone2.ereading.ui.account.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.response.ListHistory;
import com.dtu.capstone2.ereading.network.response.ListHistoryResponse;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
public class HistoryFragment extends BaseFragment {
    private RecyclerView mRecycleListView;
    private List<ListHistory> mListHistory;
    private ImageView mImageListHistoryBack;
    HistoryViewModel historyViewModal = new HistoryViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecycleListView = view.findViewById(R.id.recycler_list_history);
        mImageListHistoryBack = view.findViewById(R.id.image_back_list_history);
        mListHistory = new ArrayList<>();

        getManagerSubscribe().add(historyViewModal.getListHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListHistoryResponse>() {
                    @Override
                    public void accept(ListHistoryResponse listHistoryResponse) throws Exception {
                        mListHistory = listHistoryResponse.getListData();
                        Log.e("xxx", listHistoryResponse.toString());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        mRecycleListView.setLayoutManager(layoutManager);
                        HistoryAdapter arrayAdapter = new HistoryAdapter(mListHistory);
                        mRecycleListView.setAdapter(arrayAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showApiErrorDialog();
                    }
                }));

        mImageListHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
