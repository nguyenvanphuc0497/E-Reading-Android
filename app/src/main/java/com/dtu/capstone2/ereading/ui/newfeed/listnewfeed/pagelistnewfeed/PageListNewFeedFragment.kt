package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.pagelistnewfeed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.newfeed.translate.TranslateNewFeedFragment
import com.dtu.capstone2.ereading.ui.utils.BaseFragment
import com.dtu.capstone2.ereading.ui.utils.observeOnUiThread
import kotlinx.android.synthetic.main.fragment_list_new_feed_page.*

/**
 * Create By Huynh Vu Ha Lan on 21/03/2019
 */
class PageListNewFeedFragment : BaseFragment() {
    companion object {
        private const val KEY_URL_END_POINT = "key_url_end_point"
        private const val KEY_TYPE_NEW_FEED = "key_type_new_feed"

        internal fun newInstant(urlEndPointRSS: String, titleFragment: String) =
                PageListNewFeedFragment().apply {
                    arguments = Bundle().apply {
                        putString(KEY_URL_END_POINT, urlEndPointRSS)
                        putString(KEY_TYPE_NEW_FEED, titleFragment)
                    }
                }
    }

    private lateinit var mViewModel: PageListNewFeedViewModel
    private lateinit var mAdapter: PageListNewFeedAdapter

    override fun initData() {
        mViewModel = PageListNewFeedViewModel().apply {
            arguments?.let {
                setmUrlEndPoint(it.getString(KEY_URL_END_POINT))
                typeNewFeed = it.getString(KEY_TYPE_NEW_FEED)
            }
        }
        mAdapter = PageListNewFeedAdapter(mViewModel.listRssItemResponse)
    }

    override fun initView(view: View?) {
        layoutSwipeRefreshListNewFeed?.run {
            setColorSchemeResources(R.color.colorPink, R.color.colorIndigo, R.color.colorLime)
            isRefreshing = true // Show tiến trình Load data lần đầu
        }
        recyclerViewPageNewFeedDisplay.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    override fun initEvent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_list_new_feed_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEventsView()
    }

    private fun initEventsView() {
        layoutSwipeRefreshListNewFeed.setOnRefreshListener { loadDataFromServer() }
        mAdapter.onItemClick = { position ->
            addFragment(
                    R.id.layoutPageNewFeedContainer,
                    TranslateNewFeedFragment.newInstant(
                            mViewModel.listRssItemResponse[position].link!!,
                            mViewModel.typeNewFeed
                    ),
                    true
            )
        }
        loadDataFromServer()
    }

    private fun loadDataFromServer() {
        managerSubscribe.add(
                mViewModel.newsFeedFromServerBBCPopularTopStories
                        .observeOnUiThread()
                        .subscribe({
                            mAdapter.notifyDataSetChanged()
                            layoutSwipeRefreshListNewFeed.isRefreshing = false
                        }, {
                            Log.e("Xxx",it.toString())
                            showApiErrorDialog()
                            layoutSwipeRefreshListNewFeed.isRefreshing = false
                        })
        )
    }
}
