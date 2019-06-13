package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.dtu.capstone2.ereading.ui.model.ItemListNewFeedPager

/**
 * Create by Nguyen Van Phuc on 4/8/19
 */
class ListNewFeedPagerAdapter(fm: FragmentManager?, private val mItemListNewFeedPagers: List<ItemListNewFeedPager>) : FragmentStatePagerAdapter(fm) {
    companion object {
        internal const val KEY_URL_END_POINT = "key_url_end_point"
        internal const val KEY_TYPE_NEW_FEED = "key_type_new_feed"
    }

    override fun getItem(i: Int): Fragment = mItemListNewFeedPagers[i].fragment?.apply {
        arguments = Bundle().apply {
            putString(KEY_URL_END_POINT, mItemListNewFeedPagers[i].urlEndPointRSS)
            putString(KEY_TYPE_NEW_FEED, mItemListNewFeedPagers[i].titleFragment)
        }
    } ?: Fragment()

    override fun getCount(): Int = mItemListNewFeedPagers.size
}
