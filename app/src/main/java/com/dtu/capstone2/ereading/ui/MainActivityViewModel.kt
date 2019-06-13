package com.dtu.capstone2.ereading.ui

import com.dtu.capstone2.ereading.ui.account.PageAccountFragment
import com.dtu.capstone2.ereading.ui.model.MainPage
import com.dtu.capstone2.ereading.ui.newfeed.PageNewFeedFragment

/**
 * Create by Nguyen Van Phuc on 2019-06-13
 */
class MainActivityViewModel {
    private var mListFragment = mutableListOf<MainPage>()

    internal fun initListFragment() = mListFragment.apply {
        clear()
        add(MainPage(PageNewFeedFragment(), "Tin tức"))
//        mListFragment?.add(MainPage(PageHomeFragment(), "Trang chủ"))
        add(MainPage(PageAccountFragment(), "Tài khoản"))
    }
}
