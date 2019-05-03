package com.dtu.capstone2.ereading.ui.favorite;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.remote.EReadingRemoteDataSource;
import com.dtu.capstone2.ereading.network.request.DataFavoriteReponse;

import io.reactivex.Single;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class FavoriteViewModel {
    EReadingRepository eReadingRepository = new EReadingRepository();

    public Single<DataFavoriteReponse> getDataFavorite()
    {
        return eReadingRepository.getDataFavorite();
    }
}
