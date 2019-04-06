package com.dtu.capstone2.ereading.ui.newfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.model.ItemPageNewFeed;
import com.dtu.capstone2.ereading.ui.newfeed.newfeeddisplay.NewFeedDisplayFragment;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
public class PageNewFeedFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private PageNewFeedAdapter mAdapter;
    private List<ItemPageNewFeed> mItemPageNewFeeds;

    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_page_new_feed, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerViewPageNewFeed);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new PageNewFeedAdapter(mItemPageNewFeeds);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initEventsView();
    }

    public void initData() {
        mItemPageNewFeeds = new ArrayList<>();

        mItemPageNewFeeds.add(new ItemPageNewFeed("", "CNN News"));
        mItemPageNewFeeds.add(new ItemPageNewFeed("", "Vietnamnet"));
        mItemPageNewFeeds.add(new ItemPageNewFeed("", "BCC News"));
    }

    public void initEventsView() {
        mAdapter.setmItemPageNewFeeds(new PageNewFeedAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position) {
                //TODO Send position to Activity để mở fragment
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.layoutContainerNewFeed, new NewFeedDisplayFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
