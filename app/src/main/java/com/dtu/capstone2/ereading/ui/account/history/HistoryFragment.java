package com.dtu.capstone2.ereading.ui.account.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.response.ListHistoryResponse;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
public class HistoryFragment extends BaseFragment {
    private HistoryViewModel viewModel;
    private HistoryAdapter adapter;

    private RecyclerView mRecycleListView;
    private ImageView mImageListHistoryBack;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new HistoryViewModel();
        adapter = new HistoryAdapter(viewModel.getListHistory());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecycleListView = view.findViewById(R.id.recycler_list_history);
        mImageListHistoryBack = view.findViewById(R.id.image_back_list_history);
        refreshLayout = view.findViewById(R.id.layout_swipe_refresh_list_history);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initEventView();
        initData();
    }

    private void initView() {
        mRecycleListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleListView.setAdapter(adapter);
        refreshLayout.setRefreshing(true);
    }

    private void initEventView() {
        mImageListHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
    }

    private void getDataFromServer() {
        getManagerSubscribe().add(viewModel.getListHistoryFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListHistoryResponse>() {
                    @Override
                    public void accept(ListHistoryResponse listHistoryResponse) throws Exception {
                        refreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showApiErrorDialog();
                    }
                }));
    }

    private void initData() {
        getDataFromServer();
    }
}
