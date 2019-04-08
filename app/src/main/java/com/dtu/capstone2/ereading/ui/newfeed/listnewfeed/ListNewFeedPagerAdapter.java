package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dtu.capstone2.ereading.ui.model.ItemListNewFeedPager;

import java.util.List;

/**
 * Create by Nguyen Van Phuc on 4/8/19
 */
public class ListNewFeedPagerAdapter extends FragmentStatePagerAdapter {
    private List<ItemListNewFeedPager> mItemListNewFeedPagers;

    ListNewFeedPagerAdapter(FragmentManager fm, List<ItemListNewFeedPager> itemListNewFeedPagers) {
        super(fm);
        mItemListNewFeedPagers = itemListNewFeedPagers;
    }

    @Override
    public Fragment getItem(int i) {
        return mItemListNewFeedPagers.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return mItemListNewFeedPagers.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mItemListNewFeedPagers.get(position).getTitleFragment();
    }
}
