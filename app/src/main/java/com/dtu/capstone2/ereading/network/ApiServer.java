package com.dtu.capstone2.ereading.network;

import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;
import com.dtu.capstone2.ereading.network.response.BBCRssResponse;
import com.dtu.capstone2.ereading.network.response.RssResponse;
import com.dtu.capstone2.ereading.network.response.Token;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("edition.rss")
    Single<RssResponse> getNewsFromCNN();

    @GET("{endpoint}")
    Single<BBCRssResponse> getNewsFeedFromServerBBC(@Path(value = "endpoint", encoded = true) String url);
}
