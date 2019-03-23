package com.dtu.capstone2.ereadingandroid.network;

import com.dtu.capstone2.ereadingandroid.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereadingandroid.network.request.AddFavoriteRequest;
import com.dtu.capstone2.ereadingandroid.network.request.DataStringReponse;
import com.dtu.capstone2.ereadingandroid.network.response.Token;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    /**
     *
     * @param paraFavorite
     * @return
     */
    @POST("addfavorite")
    Single<Boolean>AddFavoriteServer(@Body AddFavoriteRequest paraFavorite );
    /**
     *
     * @param paraReponse
     * @return
     */
    @GET("v1/procresstext")
    Single<DataStringReponse>GetDataStringReponse(@Query("work") String paraReponse);


}
