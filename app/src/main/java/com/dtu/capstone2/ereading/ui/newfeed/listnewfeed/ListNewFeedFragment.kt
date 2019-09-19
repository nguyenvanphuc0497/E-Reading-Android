package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.model.ItemListNewFeedPager
import com.dtu.capstone2.ereading.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list_new_feed.*

/**
 * Create by Nguyen Van Phuc on 4/8/19
 */
class ListNewFeedFragment : BaseFragment() {
    companion object {
        private const val KEY_POSITION_GROUP_NEW_FEED = "position_group_new_feed"

        internal fun newInstant(listNewsFeed: ArrayList<ItemListNewFeedPager>) = ListNewFeedFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(KEY_POSITION_GROUP_NEW_FEED, listNewsFeed)
            }
        }
    }

    private lateinit var mAdapter: ListNewFeedPagerAdapter
    private lateinit var mViewModel: ListNewFeedViewModel

    override fun initData() {
        mViewModel = ListNewFeedViewModel().apply {
            arguments?.let { arguments ->
                listRssNewFeed = arguments.getParcelableArrayList<ItemListNewFeedPager>(KEY_POSITION_GROUP_NEW_FEED)?.toMutableList()
                        ?: mutableListOf()
            }
            mAdapter = ListNewFeedPagerAdapter(childFragmentManager, listRssNewFeed)
        }
    }

    override fun initView(view: View?) {
        viewPagerListNewFeed?.apply {
            adapter = mAdapter
            offscreenPageLimit = 1
            tabLayoutListNewFeed?.setupWithViewPager(this, true)
        }
        tvListNewFeedTitle?.text = mViewModel.listRssNewFeed[0].titleFragment
    }

    override fun initEvent() {
        imgListNewFeedBack?.setOnClickListener { activity!!.onBackPressed() }

        viewPagerListNewFeed?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvListNewFeedTitle?.text = mViewModel.listRssNewFeed[position].titleFragment
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_list_new_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }
}
