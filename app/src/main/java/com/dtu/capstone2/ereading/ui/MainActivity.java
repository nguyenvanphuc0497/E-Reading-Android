package com.dtu.capstone2.ereading.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.account.AccountFragment;
import com.dtu.capstone2.ereading.ui.home.HomeFragment;
import com.dtu.capstone2.ereading.ui.model.MainPage;
import com.dtu.capstone2.ereading.ui.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPagerMain;
    private TabLayout mTabLayout;
    private MainPagerAdapter mMainPagerAdapter;
    private List<MainPage> mListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mListFragment);
        mViewPagerMain.setAdapter(mMainPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPagerMain);
    }

    private void initView() {
        mViewPagerMain = findViewById(R.id.viewPagerMain);
        mTabLayout = findViewById(R.id.tabLayoutMain);
    }

    private void initData() {
        mListFragment = new ArrayList<>();
        mListFragment.add(new MainPage(new NewsFragment(), "News"));
        mListFragment.add(new MainPage(new HomeFragment(), "Home"));
        mListFragment.add(new MainPage(new AccountFragment(), "Account"));
    }
}