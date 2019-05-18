package com.dtu.capstone2.ereading.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.account.PageAccountFragment
import com.dtu.capstone2.ereading.ui.model.MainPage
import com.dtu.capstone2.ereading.ui.newfeed.PageNewFeedFragment
import com.dtu.capstone2.ereading.ui.utils.BaseActivity
import java.util.*


class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    private var mViewPagerMain: ViewPager? = null
    private var mTabLayout: TabLayout? = null
    private var mMainPagerAdapter: MainPagerAdapter? = null
    private var mListFragment: MutableList<MainPage>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()

        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager, mListFragment)
        mViewPagerMain?.adapter = mMainPagerAdapter
        mViewPagerMain?.offscreenPageLimit = 3
        mTabLayout?.setupWithViewPager(mViewPagerMain)

        mTabLayout?.getTabAt(0)?.setIcon(R.drawable.ic_news_feed_16)
        mTabLayout?.getTabAt(1)?.setIcon(R.drawable.ic_account_16)

        for (i in 0 until (mTabLayout?.tabCount ?: 0)) {
            mTabLayout?.getTabAt(i)?.apply {
                if (i != 0) {
                    icon?.alpha = 100
                }
            }
        }

        mViewPagerMain?.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                mTabLayout?.getTabAt(0)?.icon?.alpha = 255
                mTabLayout?.getTabAt(1)?.icon?.alpha = 100
            }
            1 -> {
                mTabLayout?.getTabAt(0)?.icon?.alpha = 100
                mTabLayout?.getTabAt(1)?.icon?.alpha = 255
            }
        }
    }

    private fun initView() {
        mViewPagerMain = findViewById(R.id.viewPagerMain)
        mTabLayout = findViewById(R.id.tabLayoutMain)
    }

    private fun initData() {
        mListFragment = ArrayList()
        mListFragment!!.add(MainPage(PageNewFeedFragment(), "Tin tức"))
//        mListFragment!!.add(MainPage(PageHomeFragment(), "Trang chủ"))
        mListFragment!!.add(MainPage(PageAccountFragment(), "Tài khoản"))
    }
}
