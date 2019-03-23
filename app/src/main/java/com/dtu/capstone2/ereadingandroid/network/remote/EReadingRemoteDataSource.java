package com.dtu.capstone2.ereadingandroid.network.remote;

import com.dtu.capstone2.ereadingandroid.network.ApiClient;
import com.dtu.capstone2.ereadingandroid.network.ApiServer;
import com.dtu.capstone2.ereadingandroid.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereadingandroid.network.request.AddFavoriteRequest;
import com.dtu.capstone2.ereadingandroid.network.request.DataStringReponse;
import com.dtu.capstone2.ereadingandroid.network.response.Token;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
public class EReadingRemoteDataSource {
    private ApiServer mApiServer = ApiClient.getInstants().createServer();

    public Single<Token> login(AccountLoginRequest account) {
        return mApiServer.loginForServer(account);
    }

    public Single<Boolean> addFavorite(AddFavoriteRequest paraFavorite){
        return mApiServer.AddFavoriteServer(paraFavorite);
    }
    public Single<DataStringReponse> GetDataStringReponseRemote(String paraReponse){
        return mApiServer.GetDataStringReponse(paraReponse);
    }
}
