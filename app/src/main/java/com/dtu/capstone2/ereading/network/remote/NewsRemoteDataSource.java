package com.dtu.capstone2.ereading.network.remote;

import com.dtu.capstone2.ereading.network.ApiClient;
import com.dtu.capstone2.ereading.network.ApiServer;
import com.dtu.capstone2.ereading.network.response.RssResponse;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
public class NewsRemoteDataSource {
    private ApiServer mApiServer = ApiClient.getInstants().createServer();

    public Single<RssResponse> getNewsFromServer() {
        return mApiServer.getNewsFromCNN();
    }
}
