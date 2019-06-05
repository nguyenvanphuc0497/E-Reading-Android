package com.dtu.capstone2.ereading.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.account.PageAccountFragment
import com.dtu.capstone2.ereading.ui.model.MainPage
import com.dtu.capstone2.ereading.ui.newfeed.PageNewFeedFragment
import com.dtu.capstone2.ereading.ui.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    private var mMainPagerAdapter: MainPagerAdapter? = null
    private var mListFragment: MutableList<MainPage>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager, mListFragment)
        view_pager_main?.adapter = mMainPagerAdapter
        view_pager_main?.offscreenPageLimit = 3
        tab_layout_main?.setupWithViewPager(view_pager_main)

        tab_layout_main?.getTabAt(0)?.setIcon(R.drawable.ic_news_feed_16)
        tab_layout_main?.getTabAt(1)?.setIcon(R.drawable.ic_account_16)

        for (i in 0 until (tab_layout_main?.tabCount ?: 0)) {
            tab_layout_main?.getTabAt(i)?.apply {
                if (i != 0) {
                    icon?.alpha = 100
                }
            }
        }

        view_pager_main?.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                tab_layout_main?.getTabAt(0)?.icon?.alpha = 255
                tab_layout_main?.getTabAt(1)?.icon?.alpha = 100
            }
            1 -> {
                tab_layout_main?.getTabAt(0)?.icon?.alpha = 100
                tab_layout_main?.getTabAt(1)?.icon?.alpha = 255
            }
        }
    }

    private fun initData() {
        mListFragment = ArrayList()
        mListFragment?.add(MainPage(PageNewFeedFragment(), "Tin tức"))
//        mListFragment?.add(MainPage(PageHomeFragment(), "Trang chủ"))
        mListFragment?.add(MainPage(PageAccountFragment(), "Tài khoản"))
    }
}
