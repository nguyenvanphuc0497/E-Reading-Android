package com.dtu.capstone2.ereadingandroid.ui;
import com.dtu.capstone2.ereadingandroid.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereadingandroid.network.request.AddFavoriteRequest;
import com.dtu.capstone2.ereadingandroid.network.request.DataStringReponse;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
public class MainActivityViewModel {
    EReadingRepository Repository = new EReadingRepository();
    String dataReponse ;
    /*
    * Đây là funtion send request add favorite*/
    public void addFavoriteMD(int idUser, int idVocabulary)
    {
        Repository.addFavorite(new AddFavoriteRequest(idUser,idVocabulary));

    }
    /*
    * đây là function nhận reponse sau khi request translate*/
    public Single<DataStringReponse> GetDataStringReponse(String paraString)
    {
        return Repository.GetDataStringReponse(paraString);

    }
}
