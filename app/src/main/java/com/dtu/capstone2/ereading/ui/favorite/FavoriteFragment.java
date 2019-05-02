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

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.DataFavoriteReponse;
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
public class FavoriteFragment extends BaseFragment {
    private RecyclerView recycleListView;
    private List<listFavorite> listFavorite;
    private ImageView imageListFavoriteBack;
    private int iduser;
    FavoriteViewModal favoriteViewModal = new FavoriteViewModal();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recycleListView = view.findViewById(R.id.recycleviewFavorite);
        imageListFavoriteBack = view.findViewById(R.id.imgListFavoriteBack);
        listFavorite=new ArrayList<>();
        favoriteViewModal.getDataFavorite()
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
                        CustomListFavorite arrayAdapter = new CustomListFavorite(listFavorite,getContext());
                        recycleListView.setAdapter(arrayAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("xxx", throwable.toString()+"bug neef");
                    }
                });
        imageListFavoriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
