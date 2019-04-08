package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.response.BBCRssResponse;
import com.dtu.capstone2.ereading.ui.newfeed.displayanewfeed.DisplayNewFeedFragment;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create By Huynh Vu Ha Lan on 21/03/2019
 */
public class ListNewFeedFragment extends BaseFragment {
    public static final String KEY_URL_NEW_FEED = "key_url_new_feed";

    private ListNewFeedViewModel mViewModel;
    private ListNewFeedAdapter mAdapter;
    private RecyclerView mRecyclerViewFeedDisplay;
    private SwipeRefreshLayout mSwipeRefresh;
    private ImageView mImgActionBarBack;

    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ListNewFeedViewModel();
        mAdapter = new ListNewFeedAdapter(mViewModel.getListRssItemResponse(), getActivity());
        // Show tiến trình Load data lần đầu
        showLoadingDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_list_new_feed, container, false);
        mRecyclerViewFeedDisplay = view.findViewById(R.id.recyclerViewPageNewFeedDisplay);
        mSwipeRefresh = view.findViewById(R.id.layoutSwipeRefreshListNewFeed);
        mImgActionBarBack = view.findViewById(R.id.imgListNewFeedBack);

        mSwipeRefresh.setColorSchemeResources(R.color.colorPink, R.color.colorIndigo, R.color.colorLime);
        initEventsView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewFeedDisplay.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewFeedDisplay.setAdapter(mAdapter);
        loadDataFromServer();
    }

    private void initEventsView() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataFromServer();
            }
        });
        mImgActionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mAdapter.setmOnItemListener(new ListNewFeedAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                String urlNewFeed = mViewModel.getListRssItemResponse().get(position).getLink();
                bundle.putString(KEY_URL_NEW_FEED, urlNewFeed);
                Fragment fragment = new DisplayNewFeedFragment();
                fragment.setArguments(bundle);


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.layoutPageNewFeedContainer, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void loadDataFromServer() {
        mViewModel.getNewsFeedFromServerBBCPopularTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<BBCRssResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(BBCRssResponse rssResponse) {
                dismissLoadingDialog();

                mAdapter.notifyDataSetChanged();
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                showApiErrorDialog();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}