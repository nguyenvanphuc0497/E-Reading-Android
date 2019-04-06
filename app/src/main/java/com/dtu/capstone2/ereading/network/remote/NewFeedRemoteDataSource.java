package com.dtu.capstone2.ereading.network.remote;

import com.dtu.capstone2.ereading.network.ApiClient;
import com.dtu.capstone2.ereading.network.ApiServer;
import com.dtu.capstone2.ereading.network.response.RssResponse;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
public class NewFeedRemoteDataSource {
    private ApiServer mApiServer = ApiClient.getInstants().createServerXml("");

    public Single<RssResponse> getNewFeedFromServerCNN() {
        return mApiServer.getNewsFromCNN();
    }
}
