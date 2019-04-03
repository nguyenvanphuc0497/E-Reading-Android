package com.dtu.capstone2.ereading.network;

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

<<<<<<< HEAD
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
    /**
     *
     * @param accountLoginRequest
     * @return
     */
    @POST("signin")
    Single<DataLoginRequest>GetDataLoginRequest(@Body AccountLoginRequest accountLoginRequest );



=======
    @POST("register")
    Single<AccountRegisterRequest> regiterAccount(@Body AccountRegisterRequest accountRegisterRequest);
>>>>>>> develop
}
