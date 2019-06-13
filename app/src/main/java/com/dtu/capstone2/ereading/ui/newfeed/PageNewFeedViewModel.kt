package com.dtu.capstone2.ereading.ui.newfeed

import com.dtu.capstone2.ereading.ui.model.ItemPageNewFeed

/**
 * Create by Nguyen Van Phuc on 2019-06-13
 */
class PageNewFeedViewModel {
    private var mItemPageNewFeeds = mutableListOf<ItemPageNewFeed>()

    internal fun initDataGroup() = mItemPageNewFeeds.apply {
        clear()
        mItemPageNewFeeds.add(ItemPageNewFeed("https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif", "BCC Popular news"))
        mItemPageNewFeeds.add(ItemPageNewFeed("https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif", "BCC Global and UK news"))
        mItemPageNewFeeds.add(ItemPageNewFeed("https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif", "BCC Sports news"))
        mItemPageNewFeeds.add(ItemPageNewFeed("", "Vietnamnet English"))
    }
}
