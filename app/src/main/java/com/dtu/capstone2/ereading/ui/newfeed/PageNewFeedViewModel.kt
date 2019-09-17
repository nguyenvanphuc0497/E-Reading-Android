package com.dtu.capstone2.ereading.ui.newfeed

import com.dtu.capstone2.ereading.ui.model.ItemListNewFeedPager
import com.dtu.capstone2.ereading.ui.model.ItemPageNewFeed

/**
 * Create by Nguyen Van Phuc on 2019-06-13
 */
class PageNewFeedViewModel {
    private var mItemPageNewFeeds = mutableListOf<ItemPageNewFeed>()

    internal fun initDataGroup() = mItemPageNewFeeds.apply {
        clear()
        mItemPageNewFeeds.add(ItemPageNewFeed("https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif",
                "BCC Popular news",
                initListNewsFeedBBCPopular()))
        mItemPageNewFeeds.add(ItemPageNewFeed("https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif",
                "BCC Global and UK news",
                initListNewsFeedBBCGlobalAndUK()))
        mItemPageNewFeeds.add(ItemPageNewFeed("https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif",
                "BCC Sports news",
                initListNewsFeedBBCSports()))
        mItemPageNewFeeds.add(ItemPageNewFeed("", "Vietnamnet English", initListNewsFeedVietnamNet()))
        mItemPageNewFeeds.add(ItemPageNewFeed("", "VN Express", initListNewsFeedVNExpress()))
    }

    private fun initListNewsFeedBBCPopular() = arrayListOf<ItemListNewFeedPager>().apply {
        clear()
        add(ItemListNewFeedPager("Top Stories", "news/rss.xml"))
        add(ItemListNewFeedPager("World", "news/world/rss.xml"))
        add(ItemListNewFeedPager("UK", "news/uk/rss.xml"))
        add(ItemListNewFeedPager("Business", "news/business/rss.xml"))
        add(ItemListNewFeedPager("Politics", "news/politics/rss.xml"))
        add(ItemListNewFeedPager("Health", "news/health/rss.xml"))
        add(ItemListNewFeedPager("Education & Family", "news/education/rss.xml"))
        add(ItemListNewFeedPager("Science & Environment", "news/science_and_environment/rss.xml"))
        add(ItemListNewFeedPager("Technology", "news/technology/rss.xml"))
        add(ItemListNewFeedPager("Entertainment & Arts", "news/entertainment_and_arts/rss.xml"))
    }

    private fun initListNewsFeedBBCGlobalAndUK() = arrayListOf<ItemListNewFeedPager>().apply {
        clear()
        add(ItemListNewFeedPager("Africa", "news/world/africa/rss.xml"))
        add(ItemListNewFeedPager("Asia", "news/world/asia/rss.xml"))
        add(ItemListNewFeedPager("Europe", "news/world/europe/rss.xml"))
        add(ItemListNewFeedPager("Latin America", "news/world/latin_america/rss.xml"))
        add(ItemListNewFeedPager("Middle East", "news/world/middle_east/rss.xml"))
        add(ItemListNewFeedPager("US & Canada", "news/world/us_and_canada/rss.xml"))
        add(ItemListNewFeedPager("England", "news/england/rss.xml"))
        add(ItemListNewFeedPager("Northern Ireland", "news/northern_ireland/rss.xml"))
        add(ItemListNewFeedPager("Scotland", "news/scotland/rss.xml"))
        add(ItemListNewFeedPager("Wales", "news/wales/rss.xml"))
    }

    private fun initListNewsFeedVietnamNet() = arrayListOf<ItemListNewFeedPager>().apply {
        clear()
        add(ItemListNewFeedPager("Homepage", "https://english.vietnamnet.vn/rss/top-hot-news.rss"))
        add(ItemListNewFeedPager("Special Reports", "https://english.vietnamnet.vn/rss/special-reports.rss"))
        add(ItemListNewFeedPager("Vietnam In Photos", "https://english.vietnamnet.vn/rss/vietnam-in-photos.rss"))
        add(ItemListNewFeedPager("What's On", "https://english.vietnamnet.vn/rss/what-s-on.rss"))
    }

    private fun initListNewsFeedVNExpress() = arrayListOf<ItemListNewFeedPager>().apply {
        clear()
        add(ItemListNewFeedPager("New Feed", "https://e.vnexpress.net/rss/news.rss"))
    }

    private fun initListNewsFeedBBCSports() = arrayListOf<ItemListNewFeedPager>().apply {
        clear()
        add(ItemListNewFeedPager("Top Sports", "sport/rss.xml"))
    }
}
