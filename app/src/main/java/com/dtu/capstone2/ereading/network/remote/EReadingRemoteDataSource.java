package com.dtu.capstone2.ereading.network.remote;

import com.dtu.capstone2.ereading.network.ApiClient;
import com.dtu.capstone2.ereading.network.ApiServer;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
<<<<<<< HEAD
import com.dtu.capstone2.ereading.network.request.AddFavoriteRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataStringReponse;
=======
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
>>>>>>> develop
import com.dtu.capstone2.ereading.network.response.Token;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
public class EReadingRemoteDataSource {
    private ApiServer mApiServer = ApiClient.getInstants().createServer();

    public Single<Token> login(AccountLoginRequest account) {
        return mApiServer.loginForServer(account);
    }

<<<<<<< HEAD
    public Single<Boolean> addFavorite(AddFavoriteRequest paraFavorite) {
        return mApiServer.AddFavoriteServer(paraFavorite);
    }

    public Single<DataStringReponse> GetDataStringReponseRemote(String paraReponse) {
        return mApiServer.GetDataStringReponse(paraReponse);
    }
    public  Single<DataLoginRequest>GetDataLoginRequest(AccountLoginRequest accountLoginRequest)
    {
        return  mApiServer.GetDataLoginRequest(accountLoginRequest);
=======
    public Single<AccountRegisterRequest> registerNewAccount(AccountRegisterRequest accountRegisterRequest) {
        return mApiServer.regiterAccount(accountRegisterRequest);
>>>>>>> develop
    }
}
