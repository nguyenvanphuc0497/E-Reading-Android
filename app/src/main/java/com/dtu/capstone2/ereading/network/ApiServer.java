package com.dtu.capstone2.ereading.network;

import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
import com.dtu.capstone2.ereading.network.request.AddFavoriteRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataStringReponse;
import com.dtu.capstone2.ereading.network.response.BBCRssResponse;
import com.dtu.capstone2.ereading.network.response.LevelEnglishReponse;
import com.dtu.capstone2.ereading.network.response.LevelUserResponse;
import com.dtu.capstone2.ereading.network.response.RssResponse;
import com.dtu.capstone2.ereading.network.response.Token;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Create by Nguyen Van Phuc on 2/20/19
 */
public interface ApiServer {
    /**
     * Login for server
     */
    @POST("login")
    Single<Token> loginForServer(@Body AccountLoginRequest accountLoginRequest);

    @POST("addfavorite")
    Single<Boolean> AddFavoriteServer(@Body AddFavoriteRequest paraFavorite);

    @GET("v1/detectword")
    Single<DataStringReponse> GetDataStringReponse(@Query("words") String paraReponse);

    @POST("signin")
    Single<DataLoginRequest> GetDataLoginRequest(@Body AccountLoginRequest accountLoginRequest);

    @POST("register")
    Single<AccountRegisterRequest> registerAccount(@Body AccountRegisterRequest accountRegisterRequest);

    @GET("edition.rss")
    Single<RssResponse> getNewsFromCNN();

    @GET("{endpoint}")
    Single<BBCRssResponse> getNewsFeedFromServerBBC(@Path(value = "endpoint", encoded = true) String url);

    @GET("v1/portal/level")
    Single<LevelEnglishReponse> getListLevelEnglish();

    @POST("v1/portal/level")
    Single<LevelUserResponse> setLevelEnglishForUser(@Query("level_position") int levelPosition);
}
