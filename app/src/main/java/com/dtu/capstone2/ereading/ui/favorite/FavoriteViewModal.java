package com.dtu.capstone2.ereading.ui.favorite;

import com.dtu.capstone2.ereading.network.remote.EReadingRemoteDataSource;
import com.dtu.capstone2.ereading.network.request.DataFavoriteReponse;

import io.reactivex.Single;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class FavoriteViewModal {
    EReadingRemoteDataSource eReadingRemoteDataSource = new EReadingRemoteDataSource();

    public Single<DataFavoriteReponse> getDataFavorite(int iduser)
    {
        return eReadingRemoteDataSource.getDataFavorite(iduser);
    }
}
