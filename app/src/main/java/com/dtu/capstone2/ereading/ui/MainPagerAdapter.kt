package com.dtu.capstone2.ereading.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import com.dtu.capstone2.ereading.ui.model.MainPage

class MainPagerAdapter(fm: FragmentManager, private val mFragmentList: List<MainPage>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i: Int): Fragment = mFragmentList[i].fragment

    override fun getCount() = mFragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = mFragmentList[position].titleFragment
}
