package com.dtu.capstone2.ereading.ui.favorite;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.DataFavoriteResponse;
import com.dtu.capstone2.ereading.network.request.Favorite;
import com.dtu.capstone2.ereading.network.request.FavoriteDeletedResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Create by Vo The Doan on 04/30/2019
 */
class FavoriteViewModel {
    private EReadingRepository eReadingRepository = new EReadingRepository();
    private List<Favorite> listFavorite = new ArrayList<>();

    Single<DataFavoriteResponse> getDataFavoriteFromServer() {
        return eReadingRepository.getDataFavorite()
                .doOnSuccess(new Consumer<DataFavoriteResponse>() {
                    @Override
                    public void accept(DataFavoriteResponse dataFavoriteReponse) throws Exception {
                        listFavorite.clear();
                        listFavorite.addAll(dataFavoriteReponse.getListData());
                    }
                });
    }

    Single<FavoriteDeletedResponse> deleteFavorite(int id) {
        return eReadingRepository.deleteFavorite(id);
    }

    List<Favorite> getListFavorite() {
        return listFavorite;
    }
}
