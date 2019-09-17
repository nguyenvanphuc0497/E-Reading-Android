package com.dtu.capstone2.ereading.network;

import com.dtu.capstone2.ereading.network.request.*;
import com.dtu.capstone2.ereading.network.response.*;
import io.reactivex.Single;
import retrofit2.http.*;

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

    @POST("v1/translate/word")
    Single<DataStringReponse> GetDataStringReponse(@Body DetectWordRequest detectWordRequest, @Query("level_name") String nameLevel);

    @POST("signin")
    Single<DataLoginRequest> GetDataLoginRequest(@Body AccountLoginRequest accountLoginRequest);

    @POST("register")
    Single<AccountRegisterRequest> registerAccount(@Body AccountRegisterRequest accountRegisterRequest);

    @GET("edition.rss")
    Single<RssResponse> getNewsFromCNN();

    @GET("{endpoint}")
    Single<BaseFeedResponse> getNewsFeedFromServerBBC(@Path(value = "endpoint", encoded = true) String url);

    @GET("v1/portal/level")
    Single<ListLevelEnglishResponse> getListLevelEnglish();

    @POST("v1/portal/level")
    Single<LevelUserResponse> setLevelEnglishForUser(@Query("level_position") int levelPosition);

    @GET("v1/portal/favorite")
    Single<DataFavoriteResponse> getDataFavorite(@Query("page") int page);

    @POST("v1/portal/favorite")
    Single<DetailResponse> setListVocabularyFavorite(@Body ListVocabularyFavoriteRequest listVocabularyFavoriteRequest);

    @POST("v1/translate/feed")
    Single<DataStringReponse> translateNewFeed(@Body TranslateNewFeedRequest translateNewFeedRequest);

    @POST("v1/translate/feed/refresh")
    Single<DataStringReponse> translateNewFeedAgain(@Body TranslateNewFeedAgainRequest translateNewFeedAgainRequest);

    @GET("v1/portal/history/new_feed")
    Single<ListHistoryResponse> getListHistory(@Query("page") int page);

    @DELETE("v1/portal/favorite/{idfavorite}")
    Single<FavoriteDeletedResponse> deleteFavorite(@Path(value = "idfavorite", encoded = true) int data);
}
