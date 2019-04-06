package com.dtu.capstone2.ereading.ui.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.remote.NewsRemoteDataSource;
import com.dtu.capstone2.ereading.network.response.RssResponse;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
@SuppressLint("CheckResult")
public class NewsContainerActivity extends AppCompatActivity {
    private NewsRemoteDataSource newsRemoteDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_container);

        newsRemoteDataSource = new NewsRemoteDataSource();
        newsRemoteDataSource.getNewsFromServer().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RssResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(RssResponse feed) {
                        Log.e("xxx", "onSuccess" + feed.getArticleList().size());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("xxx", "Error" + e.toString());
                    }
                });

    }
}
