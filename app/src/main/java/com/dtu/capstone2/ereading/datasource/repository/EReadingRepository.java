package com.dtu.capstone2.ereading.datasource.repository;

import com.dtu.capstone2.ereading.network.remote.EReadingRemoteDataSource;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
import com.dtu.capstone2.ereading.network.request.AddFavoriteRequest;
import com.dtu.capstone2.ereading.network.request.DataFavoriteReponse;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataStringReponse;
import com.dtu.capstone2.ereading.network.request.DetectWordRequest;
import com.dtu.capstone2.ereading.network.request.Vocabulary;
import com.dtu.capstone2.ereading.network.response.DetailResponse;
import com.dtu.capstone2.ereading.network.response.ListLevelEnglishResponse;
import com.dtu.capstone2.ereading.network.response.Token;
import com.dtu.capstone2.ereading.ui.model.LevelEnglish;

import java.util.List;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
public class EReadingRepository {
    static private EReadingRemoteDataSource eReadingRemoteDataSource = new EReadingRemoteDataSource();

    /**
     * Day la method dung de request api HomeActivity
     */
    public Single<Token> login(AccountLoginRequest account) {
        return eReadingRemoteDataSource.login(account);
    }

    public Single<Boolean> addFavorite(AddFavoriteRequest paraFavorite) {
        return eReadingRemoteDataSource.addFavorite(paraFavorite);
    }

    public Single<DataStringReponse> GetDataStringReponse(String paraReponse, String nameLevel) {
        return eReadingRemoteDataSource.GetDataStringReponseRemote(new DetectWordRequest(paraReponse), nameLevel);
    }

    public Single<DataLoginRequest> GetDataLoginRequest(AccountLoginRequest accountLoginRequest) {
        return eReadingRemoteDataSource.GetDataLoginRequest(accountLoginRequest);
    }

    public Single<AccountRegisterRequest> registerNewAccount(AccountRegisterRequest accountRegisterRequest) {
        return eReadingRemoteDataSource.registerNewAccount(accountRegisterRequest);
    }

    public Single<ListLevelEnglishResponse> getLevelEnglishFromServer() {
        return eReadingRemoteDataSource.getLevelEnglishFromServer();
    }

    public Single<LevelEnglish> setLevelEnglishForUserToServer(int levelPosition) {
        return eReadingRemoteDataSource.setLevelEnglishForUser(levelPosition);
    }

    public Single<DetailResponse> setListVocabularyFavorite(List<Vocabulary> vocabularyList) {
        return eReadingRemoteDataSource.setListVocabularyFavorite(vocabularyList);
    }

    public Single<DataFavoriteReponse> getDataFavorite() {
        return eReadingRemoteDataSource.getDataFavorite();
    }
}
