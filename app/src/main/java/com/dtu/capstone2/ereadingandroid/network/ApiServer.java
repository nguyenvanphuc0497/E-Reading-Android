package com.dtu.capstone2.ereadingandroid.network;

import com.dtu.capstone2.ereadingandroid.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereadingandroid.network.response.Token;

import io.reactivex.Single;
import retrofit2.http.POST;

/**
 * Create by Nguyen Van Phuc on 2/20/19
 */
public interface ApiServer {
    /**
     * Login for server
     *
     * @param accountLoginRequest
     * @return
     */
    @POST("login")
    Single<Token> loginForServer(AccountLoginRequest accountLoginRequest);
}
