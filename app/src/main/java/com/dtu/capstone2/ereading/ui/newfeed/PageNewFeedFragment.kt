package com.dtu.capstone2.ereading.ui.newfeed

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.ListNewFeedFragment
import com.dtu.capstone2.ereading.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_page_new_feed.*

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
class PageNewFeedFragment : BaseFragment() {
    private lateinit var mAdapter: PageNewFeedAdapter
    private lateinit var viewModel: PageNewFeedViewModel

    override fun initData() {
        viewModel = PageNewFeedViewModel()
        mAdapter = PageNewFeedAdapter(viewModel.initDataGroup())
    }

    override fun initView(view: View?) {
        recyclerViewPageNewFeed?.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initEvent() {
        mAdapter.onItemClick = { position ->
            addFragment(R.id.layoutPageNewFeedContainer, ListNewFeedFragment.newInstant(viewModel.initDataGroup()[position].listNewFeedPager), true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_page_new_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }
}
