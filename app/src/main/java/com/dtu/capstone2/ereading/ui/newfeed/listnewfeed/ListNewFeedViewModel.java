package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed;

import com.dtu.capstone2.ereading.ui.model.ItemListNewFeedPager;
import com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.pagelistnewfeed.PageListNewFeedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Nguyen Van Phuc on 4/8/19
 */
class ListNewFeedViewModel {
    private List<ItemListNewFeedPager> mListNewFeedPagers = new ArrayList<>();

    List<ItemListNewFeedPager> getListItemPagers() {
        mListNewFeedPagers.add(new ItemListNewFeedPager(new PageListNewFeedFragment(), "News", ""));
        mListNewFeedPagers.add(new ItemListNewFeedPager(new PageListNewFeedFragment(), "News", ""));
        return mListNewFeedPagers;
    }
}
