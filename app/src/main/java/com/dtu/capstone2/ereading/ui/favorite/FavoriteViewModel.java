package com.dtu.capstone2.ereading.ui.favorite;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.DataFavoriteReponse;
import com.dtu.capstone2.ereading.network.request.DataRequestDeleteFavorite;

import io.reactivex.Single;

/**
 * Create by Vo The Doan on 04/30/2019
 */
class FavoriteViewModel {
    EReadingRepository eReadingRepository = new EReadingRepository();

    Single<DataFavoriteReponse> getDataFavorite() {
        return eReadingRepository.getDataFavorite();
    }
    Single<DataRequestDeleteFavorite> deleteFavorite(int id)
    {
        return eReadingRepository.deleteFavorite(id);
    }

}
