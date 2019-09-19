package com.dtu.capstone2.ereading.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dtu.capstone2.ereading.ui.base.BasePagerFragment
import com.dtu.capstone2.ereading.ui.base.TypeTabPager

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = BasePagerFragment.newInstance(TypeTabPager.values()[position].type)

    override fun getCount() = TypeTabPager.values().size

    override fun getPageTitle(position: Int): CharSequence? = TypeTabPager.values()[position].title
}
