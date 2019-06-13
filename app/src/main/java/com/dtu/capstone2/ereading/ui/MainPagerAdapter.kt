package com.dtu.capstone2.ereading.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.dtu.capstone2.ereading.ui.model.MainPage

class MainPagerAdapter(fm: FragmentManager, private val mFragmentList: List<MainPage>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i: Int): Fragment = mFragmentList[i].fragment

    override fun getCount() = mFragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = mFragmentList[position].titleFragment
}
