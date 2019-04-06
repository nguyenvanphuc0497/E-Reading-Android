package com.dtu.capstone2.ereading.datasource.repository;

import com.dtu.capstone2.ereading.network.remote.NewFeedRemoteDataSource;
import com.dtu.capstone2.ereading.network.response.RssResponse;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
public class NewFeedRepository {
    private NewFeedRemoteDataSource newFeedRemoteDataSource = new NewFeedRemoteDataSource();

    public Single<RssResponse> getNewFeedFromServerCNN() {
        return newFeedRemoteDataSource.getNewFeedFromServerCNN();
    }
}
