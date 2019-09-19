package com.dtu.capstone2.ereading.ui

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.base.BaseActivity
import com.dtu.capstone2.ereading.ui.base.TypeTabPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    private lateinit var mMainPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            TypeTabPager.TAB_NEW_FEED.type -> {
                tab_layout_main?.run {
                    getTabAt(0)?.icon?.alpha = 255
                    getTabAt(1)?.icon?.alpha = 100
                }
            }
            TypeTabPager.TAB_ACCOUNT.type -> {
                tab_layout_main?.run {
                    getTabAt(0)?.icon?.alpha = 100
                    getTabAt(1)?.icon?.alpha = 255
                }
            }
        }
    }

    private fun initData() {
        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager)
    }

    private fun initView() {
        view_pager_main?.apply {
            adapter = mMainPagerAdapter
            offscreenPageLimit = 3
            addOnPageChangeListener(this@MainActivity)
        }

        tab_layout_main?.apply {
            setupWithViewPager(view_pager_main)
            getTabAt(TypeTabPager.TAB_NEW_FEED.type)?.setIcon(R.drawable.ic_news_feed_16)
            getTabAt(TypeTabPager.TAB_ACCOUNT.type)?.setIcon(R.drawable.ic_account_16)
            repeat(tabCount) {
                getTabAt(it)?.run {
                    if (it != 0) {
                        icon?.alpha = 100
                    }
                }
            }
        }
    }
}
