package com.dtu.capstone2.ereading.ui.newfeed.newfeeddisplay;

import com.dtu.capstone2.ereading.datasource.repository.NewFeedRepository;
import com.dtu.capstone2.ereading.network.response.RssResponse;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
public class NewFeedDisplayViewModel {
    private NewFeedRepository mNewFeedRepository = new NewFeedRepository();

    public Single<RssResponse> getNewFeedOfServerCNN() {
        return mNewFeedRepository.getNewFeedFromServerCNN();
    }
}
