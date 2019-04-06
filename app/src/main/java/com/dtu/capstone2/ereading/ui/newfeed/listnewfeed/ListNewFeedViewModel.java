package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed;

import com.dtu.capstone2.ereading.datasource.repository.NewFeedRepository;
import com.dtu.capstone2.ereading.network.response.RssItemResponse;
import com.dtu.capstone2.ereading.network.response.RssResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
class ListNewFeedViewModel {
    private NewFeedRepository mNewFeedRepository = new NewFeedRepository();
    private List<RssItemResponse> mRssItemResponses = new ArrayList<>();

    Single<RssResponse> getNewFeedOfServerCNN() {
        return mNewFeedRepository.getNewFeedFromServerCNN().doOnSuccess(new Consumer<RssResponse>() {
            @Override
            public void accept(RssResponse rssResponse) throws Exception {
                mRssItemResponses.clear();
                mRssItemResponses.addAll(rssResponse.getRssItemResponses());
            }
        });
    }

    List<RssItemResponse> getListRssItemResponse() {
        return mRssItemResponses;
    }
}
