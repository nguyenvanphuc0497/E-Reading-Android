package com.dtu.capstone2.ereading.ui.favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ListView lvFavorite;
    private ArrayList<listFavorite> listFavorite;
    private int iduser;
    FavoriteViewModal favoriteViewModal = new FavoriteViewModal();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        lvFavorite = view.findViewById(R.id.lvFavorite);
        favoriteViewModal.getDataFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataFavoriteReponse>() {
                    @Override
                    public void accept(DataFavoriteReponse dataFavoriteReponse) throws Exception {
                        listFavorite =dataFavoriteReponse.getListData();
                        ArrayAdapter adap = new ArrayAdapter (getContext(), android.R.layout.simple_list_item_1, listFavorite);
                        lvFavorite.setAdapter(adap);
                    }
                });
        return view;
    }
}
