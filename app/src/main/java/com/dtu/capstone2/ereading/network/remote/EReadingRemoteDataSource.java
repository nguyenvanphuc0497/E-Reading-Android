package com.dtu.capstone2.ereading.network.remote;

import com.dtu.capstone2.ereading.network.ApiClient;
import com.dtu.capstone2.ereading.network.ApiServer;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
import com.dtu.capstone2.ereading.network.request.AddFavoriteRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataStringReponse;
import com.dtu.capstone2.ereading.network.response.LevelEnglishReponse;
import com.dtu.capstone2.ereading.network.response.Token;
import com.dtu.capstone2.ereading.ui.model.LevelEnglish;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
public class EReadingRemoteDataSource {
    private ApiServer mApiServer = ApiClient.getInstants().createServer();

    public Single<Token> login(AccountLoginRequest account) {
        return mApiServer.loginForServer(account);
    }

    public Single<Boolean> addFavorite(AddFavoriteRequest paraFavorite) {
        return mApiServer.AddFavoriteServer(paraFavorite);
    }

    public Single<DataStringReponse> GetDataStringReponseRemote(String paraReponse) {
        return mApiServer.GetDataStringReponse(paraReponse);
    }

    public Single<DataLoginRequest> GetDataLoginRequest(AccountLoginRequest accountLoginRequest) {
        return mApiServer.GetDataLoginRequest(accountLoginRequest);
    }

    public Single<AccountRegisterRequest> registerNewAccount(AccountRegisterRequest accountRegisterRequest) {
        return mApiServer.registerAccount(accountRegisterRequest);
    }

    public Single<List<LevelEnglish>> getLevelEnglishFromServer() {
        return mApiServer.getListLevelEnglish().map(new Function<LevelEnglishReponse, List<LevelEnglish>>() {
            @Override
            public List<LevelEnglish> apply(LevelEnglishReponse levelEnglishReponse) throws Exception {
                return levelEnglishReponse.getLevels();
            }
        });
    }
}
