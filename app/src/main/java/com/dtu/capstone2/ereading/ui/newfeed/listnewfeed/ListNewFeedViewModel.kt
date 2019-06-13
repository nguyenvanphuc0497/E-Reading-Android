package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed

import com.dtu.capstone2.ereading.ui.model.ItemListNewFeedPager
import com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.pagelistnewfeed.PageListNewFeedFragment

/**
 * Create by Nguyen Van Phuc on 4/8/19
 */
class ListNewFeedViewModel {
    private val mListNewFeedBBCPopularPagers = mutableListOf<ItemListNewFeedPager>()
    private val mListNewFeedBBCGlobalAndUKPagers = mutableListOf<ItemListNewFeedPager>()
    private val mListNewFeedBBCSportPagers = mutableListOf<ItemListNewFeedPager>()
    private val groupNewFeed = mutableListOf<List<ItemListNewFeedPager>>()
    internal var positionGroup: Int = 0

    private val listItemPagersFromSourceBBCPopular: List<ItemListNewFeedPager> = mListNewFeedBBCPopularPagers.apply {
        mListNewFeedBBCPopularPagers.clear()
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Top Stories", "news/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "World", "news/world/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "UK", "news/uk/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Business", "news/business/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Politics", "news/politics/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Health", "news/health/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Education & Family", "news/education/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Science & Environment", "news/science_and_environment/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Technology", "news/technology/rss.xml"))
        mListNewFeedBBCPopularPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Entertainment & Arts", "news/entertainment_and_arts/rss.xml"))
    }

    private val listItemPagersFromSourceBBCGlobalAndUK: List<ItemListNewFeedPager> = mListNewFeedBBCGlobalAndUKPagers.apply {
        mListNewFeedBBCGlobalAndUKPagers.clear()
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Africa", "news/world/africa/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Asia", "news/world/asia/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Europe", "news/world/europe/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Latin America", "news/world/latin_america/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Middle East", "news/world/middle_east/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "US & Canada", "news/world/us_and_canada/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "England", "news/england/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Northern Ireland", "news/northern_ireland/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Scotland", "news/scotland/rss.xml"))
        mListNewFeedBBCGlobalAndUKPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Wales", "news/wales/rss.xml"))
    }

    private val listItemPagersFromSourceBBCSports: List<ItemListNewFeedPager> = mListNewFeedBBCSportPagers.apply {
        mListNewFeedBBCSportPagers.clear()
        mListNewFeedBBCSportPagers.add(ItemListNewFeedPager(PageListNewFeedFragment(), "Top Sports", "sport/rss.xml"))
    }


    fun getGroupNewFeed(): List<List<ItemListNewFeedPager>> = groupNewFeed.apply {
        groupNewFeed.clear()
        groupNewFeed.add(listItemPagersFromSourceBBCPopular)
        groupNewFeed.add(listItemPagersFromSourceBBCGlobalAndUK)
        groupNewFeed.add(listItemPagersFromSourceBBCSports)
    }
}
