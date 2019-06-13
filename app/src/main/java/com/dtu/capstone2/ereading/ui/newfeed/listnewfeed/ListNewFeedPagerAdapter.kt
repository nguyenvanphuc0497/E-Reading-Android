package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.dtu.capstone2.ereading.ui.model.ItemListNewFeedPager
import com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.pagelistnewfeed.PageListNewFeedFragment

/**
 * Create by Nguyen Van Phuc on 4/8/19
 */
class ListNewFeedPagerAdapter(fm: FragmentManager?, private val mItemListNewFeedPagers: List<ItemListNewFeedPager>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {
        mItemListNewFeedPagers[i].also {
            return PageListNewFeedFragment.newInstant(it.urlEndPointRSS, it.titleFragment)
        }
    }

    override fun getCount(): Int = mItemListNewFeedPagers.size
}
