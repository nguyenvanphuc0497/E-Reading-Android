package com.dtu.capstone2.ereading.ui.account.favorite;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.DataFavoriteResponse;
import com.dtu.capstone2.ereading.network.request.FavoriteDeletedResponse;
import com.dtu.capstone2.ereading.ui.base.BaseFragment;

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

        initView(view);
        initScrollListener();
        initEvent();
        initData();
    }

    @Override
    public void initView(View view) {
        recycleListView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleListView.setAdapter(adapter);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeResources(R.color.colorPink, R.color.colorIndigo, R.color.colorLime);
    }

    @Override
    public void initEvent() {
        adapter.setItemDeleteOnClickListener(new FavoriteAdapter.OnItemListener() {
            @Override
            public void onItemClick(final int position) {
                showLoadingDialog();
                getManagerSubscribe().add(viewModel.deleteFavorite(position)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FavoriteDeletedResponse>() {
                            @Override
                            public void accept(FavoriteDeletedResponse dataLoginRequest) throws Exception {
                                dismissLoadingDialog();
                                Toast.makeText(getContext(), "Đã xóa thành công.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        getManagerSubscribe().add(viewModel.getDataFavoriteFromServerFirstLoad()
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
                        handleGetDataFromServerError();
                    }
                }));
    }

    private void initScrollListener() {
        recycleListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!viewModel.getLoadingMore() && viewModel.getIsCanLoadMore()) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.getSizeListFavorite() - 1) {
                        //bottom of list!
                        loadMore();
                        viewModel.setLoadingMore(true);
                    }
                }
            }
        });
    }

    private void loadMore() {
        viewModel.getListFavorite().add(null);
        adapter.notifyItemInserted(viewModel.getSizeListFavorite() - 1);


        getManagerSubscribe().add(viewModel.loadMore(viewModel.getPageData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataFavoriteResponse>() {
                    @Override
                    public void accept(DataFavoriteResponse dataFavoriteReponse) throws Exception {
                        handleLoadMoreDataSuccess(dataFavoriteReponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleGetDataFromServerError();
                    }
                }));
    }

    private void handleLoadMoreDataSuccess(DataFavoriteResponse dataFavoriteResponse) {
        refreshLayout.setRefreshing(false);
        viewModel.getListFavorite().remove(viewModel.getPositionItemLater());
        adapter.notifyItemRemoved(viewModel.getSizeListFavorite());
        viewModel.getListFavorite().addAll(dataFavoriteResponse.getListData());
        adapter.notifyDataSetChanged();
        viewModel.setLoadingMore(false);
    }

    private void handleGetDataFromServerError() {
        showApiErrorDialog();
        refreshLayout.setRefreshing(false);
        if (viewModel.getLoadingMore()) {
            viewModel.setLoadingMore(false);
            viewModel.getListFavorite().remove(viewModel.getPositionItemLater());
            adapter.notifyItemRemoved(viewModel.getSizeListFavorite());
        }
    }
}
