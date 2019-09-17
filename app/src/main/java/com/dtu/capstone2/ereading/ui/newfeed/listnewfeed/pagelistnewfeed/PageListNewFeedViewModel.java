package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.pagelistnewfeed;

import com.dtu.capstone2.ereading.datasource.repository.RssNewFeedRepository;
import com.dtu.capstone2.ereading.network.response.BaseFeedResponse;
import com.dtu.capstone2.ereading.network.response.VnExpressItemRss;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
class PageListNewFeedViewModel {
    private RssNewFeedRepository mNewFeedRepository = new RssNewFeedRepository();
    private List<VnExpressItemRss> mRssItemResponses = new ArrayList<>();
    private String mUrlEndPoint;
    private String mTypeNewFeed;

//    Single<RssResponse> getNewFeedOfServerCNN() {
//        return mNewFeedRepository.getNewFeedFromServerCNN().doOnSuccess(new Consumer<RssResponse>() {
//            @Override
//            public void accept(RssResponse rssResponse) throws Exception {
//                mRssItemResponses.clear();
//                mRssItemResponses.addAll(rssResponse.getRssItemResponses());
//            }
//        });
//    }

    List<VnExpressItemRss> getListRssItemResponse() {
        return mRssItemResponses;
    }

    Single<BaseFeedResponse> getNewsFeedFromServerBBCPopularTopStories() {
        return mNewFeedRepository.getNewsFeedFromServerBBC(mUrlEndPoint).doOnSuccess(new Consumer<BaseFeedResponse>() {
            @Override
            public void accept(BaseFeedResponse vnExpressRootRss) throws Exception {
                mRssItemResponses.clear();
                mRssItemResponses.addAll(vnExpressRootRss.getEntries());
            }
        });
    }

    void setmUrlEndPoint(String mUrlEndPoint) {
        this.mUrlEndPoint = mUrlEndPoint;
    }

    void setTypeNewFeed(String mTypeNewFeed) {
        this.mTypeNewFeed = mTypeNewFeed;
    }

    String getTypeNewFeed() {
        return mTypeNewFeed;
    }
}
