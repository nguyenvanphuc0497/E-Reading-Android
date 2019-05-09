package com.dtu.capstone2.ereading.ui.favorite;

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
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.DataFavoriteResponse;
import com.dtu.capstone2.ereading.network.request.FavoriteDeletedResponse;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class FavoriteFragment extends BaseFragment {
    private FavoriteViewModel viewModel;
    private FavoriteAdapter adapter;

    private RecyclerView recycleListView;
    private ImageView imageListFavoriteBack;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new FavoriteViewModel();
        adapter = new FavoriteAdapter(viewModel.getListFavorite());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recycleListView = view.findViewById(R.id.recycleviewFavorite);
        imageListFavoriteBack = view.findViewById(R.id.imgListFavoriteBack);
        refreshLayout = view.findViewById(R.id.layout_swipe_refresh_list_favorite);

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
        recycleListView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleListView.setAdapter(adapter);
        refreshLayout.setRefreshing(true);
    }

    private void initEventView() {
        adapter.setmItemFavorite(new FavoriteAdapter.OnItemListener() {
            @Override
            public void onItemClick(final int position) {
                showLoadingDialog();
                getManagerSubscribe().add(viewModel.deleteFavorite(viewModel.getListFavorite().get(position).getIntId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FavoriteDeletedResponse>() {
                            @Override
                            public void accept(FavoriteDeletedResponse dataLoginRequest) throws Exception {
                                dismissLoadingDialog();
                                Toast.makeText(getContext(), "Đã xóa thành công", Toast.LENGTH_LONG).show();
                                viewModel.getListFavorite().remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showApiErrorDialog();
                            }
                        }));
            }
        });
        imageListFavoriteBack.setOnClickListener(new View.OnClickListener() {
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

    private void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        getManagerSubscribe().add(viewModel.getDataFavoriteFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataFavoriteResponse>() {
                    @Override
                    public void accept(DataFavoriteResponse dataFavoriteReponse) throws Exception {
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showApiErrorDialog();
                    }
                }));
    }
}
