package com.dtu.capstone2.ereading.ui.newfeed.displayanewfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.ListNewFeedFragment;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

import org.jetbrains.annotations.Nullable;

/**
 * Create by Nguyen Van Phuc on 4/7/19
 */
public class DisplayNewFeedFragment extends BaseFragment {
    private WebView mWebViewNewFeed;
    private DisplayNewFeedViewModel mViewModel;
    private ImageView imgBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new DisplayNewFeedViewModel();

        if (getArguments() != null) {
            String parseUrl = getArguments().getString(ListNewFeedFragment.KEY_URL_NEW_FEED);
            mViewModel.setUrlNewFeed(parseUrl);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_display_new_feed, container, false);
        mWebViewNewFeed = view.findViewById(R.id.webViewDisplayNewFeed);
        mWebViewNewFeed.getSettings().setJavaScriptEnabled(true);
        imgBack = view.findViewById(R.id.imgDisplayNewFeedBack);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mWebViewNewFeed.loadUrl(mViewModel.getUrlNewFeed());
        mWebViewNewFeed.setWebViewClient(new WebViewClient());
    }
}
