package com.dtu.capstone2.ereading.network;

import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
import com.dtu.capstone2.ereading.network.response.Token;

import io.reactivex.Single;
import retrofit2.http.Body;
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
    Single<Token> loginForServer(@Body AccountLoginRequest accountLoginRequest);

    @POST("register")
    Single<AccountRegisterRequest> regiterAccount(@Body AccountRegisterRequest accountRegisterRequest);
}
