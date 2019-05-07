package com.dtu.capstone2.ereading.ui.favorite;

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
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.DataFavoriteReponse;
import com.dtu.capstone2.ereading.network.request.DataRequestDeleteFavorite;
import com.dtu.capstone2.ereading.network.request.listFavorite;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class FavoriteFragment extends BaseFragment{
    private RecyclerView recycleListView;
    private List<listFavorite> listFavorite;
    private ImageView imageListFavoriteBack;
    private ImageView imgDeleteFavorite;
    FavoriteViewModel favoriteViewModal = new FavoriteViewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recycleListView = view.findViewById(R.id.recycleviewFavorite);
        imageListFavoriteBack = view.findViewById(R.id.imgListFavoriteBack);
        listFavorite = new ArrayList<>();

        getManagerSubscribe().add(favoriteViewModal.getDataFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataFavoriteReponse>() {
                    @Override
                    public void accept(DataFavoriteReponse dataFavoriteReponse) throws Exception {
                        listFavorite = dataFavoriteReponse.getListData();
                        Log.e("xxx", listFavorite.toString());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recycleListView.setLayoutManager(layoutManager);
                        final FavoriteAdapter arrayAdapter = new FavoriteAdapter(listFavorite);
                        arrayAdapter.notifyDataSetChanged();
                        arrayAdapter.setmItemFavorite(new FavoriteAdapter.OnItemListener() {
                            @Override
                            public void onItemClick(final int position) {
                                int iditem=listFavorite.get(position).getIntId();
                                getManagerSubscribe().add(favoriteViewModal.deleteFavorite(iditem)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<DataRequestDeleteFavorite>() {
                                            @Override
                                            public void accept(DataRequestDeleteFavorite dataLoginRequest) throws Exception {
                                                Toast.makeText(getContext(),"Đã xóa thành công",Toast.LENGTH_LONG).show();
                                                listFavorite.remove(position);
                                                arrayAdapter.notifyDataSetChanged();
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                showApiErrorDialog();
                                            }
                                        }));
                            }
                        });
                        recycleListView.setAdapter(arrayAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showApiErrorDialog();
                    }
                }));
        imageListFavoriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
