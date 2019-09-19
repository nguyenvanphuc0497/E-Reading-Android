package com.dtu.capstone2.ereading.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.account.PageAccountFragment
import com.dtu.capstone2.ereading.ui.home.PageHomeFragment
import com.dtu.capstone2.ereading.ui.newfeed.PageNewFeedFragment

/**
 * Create by Nguyen Van Phuc on 2019-09-17
 */
class BasePagerFragment : Fragment() {
    companion object {
        private const val KEY_TYPE_TAB_PAGER = "key_type_tab_pager"

        internal fun newInstance(typeTabPager: Int) = BasePagerFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_TYPE_TAB_PAGER, typeTabPager)
            }
        }
    }

    private var typeTabPager = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeTabPager = it.getInt(KEY_TYPE_TAB_PAGER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_page_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.layoutPagerFragmentContainer, when (typeTabPager) {
                TypeTabPager.TAB_NEW_FEED.type -> PageNewFeedFragment()
                TypeTabPager.TAB_ACCOUNT.type -> PageAccountFragment()
                else -> PageHomeFragment()
            })
        }.commit()
    }
}
