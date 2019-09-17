package com.dtu.capstone2.ereading.ui

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    private lateinit var mMainPagerAdapter: MainPagerAdapter
    private lateinit var viewModel: MainActivityViewModel

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
            0 -> {
                tab_layout_main?.run {
                    getTabAt(0)?.icon?.alpha = 255
                    getTabAt(1)?.icon?.alpha = 100
                }
            }
            1 -> {
                tab_layout_main?.run {
                    getTabAt(0)?.icon?.alpha = 100
                    getTabAt(1)?.icon?.alpha = 255
                }
            }
        }
    }

    private fun initData() {
        viewModel = MainActivityViewModel()
        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager, viewModel.initListFragment())
    }

    private fun initView() {
        view_pager_main?.apply {
            adapter = mMainPagerAdapter
            offscreenPageLimit = 3
            addOnPageChangeListener(this@MainActivity)
        }

        tab_layout_main?.apply {
            setupWithViewPager(view_pager_main)
            getTabAt(0)?.setIcon(R.drawable.ic_news_feed_16)
            getTabAt(1)?.setIcon(R.drawable.ic_account_16)
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
