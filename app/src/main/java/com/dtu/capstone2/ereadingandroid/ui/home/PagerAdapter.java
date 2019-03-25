package com.dtu.capstone2.ereadingandroid.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dtu.capstone2.ereadingandroid.ui.translate.TranslateFragment;
import com.dtu.capstone2.ereadingandroid.ui.yourtext.YourTextFragment;
import com.dtu.capstone2.ereadingandroid.ui.news.NewsFragment;

/**
 * Create By Huynh Vu Ha Lan on 19/03/2019
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

        @Override
        public Fragment getItem(int position) {
            Fragment fr=null;
            switch (position){
                case 0:
                    fr = new NewsFragment();
                    break;
                case 1:
                    fr = new YourTextFragment();
                    break;
                case 2:
                    fr = new TranslateFragment();
                    break;
            }
            return fr;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position){
                case 0:
                    title = "News";
                    break;
                case 1:
                    title = "Your Text";
                    break;
                case 2:
                    title = "Trans";
                    break;
            }
            return title;
        }
}
