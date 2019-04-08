package com.dtu.capstone2.ereading.network.remote;

import com.dtu.capstone2.ereading.network.ApiClient;
import com.dtu.capstone2.ereading.network.ApiServer;
import com.dtu.capstone2.ereading.network.response.BBCRssResponse;
import com.dtu.capstone2.ereading.network.response.RssResponse;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
public class RssNewFeedRemoteDataSource {
    private ApiServer mApiServer = ApiClient.getInstants().createServerXml("");
    private ApiServer mApiServerBBC = ApiClient.getInstants().createServerXml("http://feeds.bbci.co.uk/");

    public Single<RssResponse> getNewFeedFromServerCNN() {
        return mApiServer.getNewsFromCNN();
    }

    public Single<BBCRssResponse> getNewsFeedFromServerBBC(String urlEndpoint) {
        return mApiServerBBC.getNewsFeedFromServerBBC(urlEndpoint);
    }
}
