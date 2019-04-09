package com.dtu.capstone2.ereading.ui.newfeed.displayanewfeed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.newfeed.translate.TranslateNewFeedFragment;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;
import com.dtu.capstone2.ereading.ui.utils.Constants;

import org.jetbrains.annotations.Nullable;

/**
 * Create by Nguyen Van Phuc on 4/7/19
 */
public class DisplayNewFeedFragment extends BaseFragment {
    private WebView mWebViewNewFeed;
    private DisplayNewFeedViewModel mViewModel;
    private ImageView imgBack;
    private ImageView imgTranslate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new DisplayNewFeedViewModel();

        if (getArguments() != null) {
            String parseUrl = getArguments().getString(Constants.KEY_URL_NEW_FEED);
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
        imgTranslate = view.findViewById(R.id.imgDisplayNewFeedTranslate);

        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEventsView();
        mWebViewNewFeed.loadUrl(mViewModel.getUrlNewFeed());
    }

    private void initEventsView() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mWebViewNewFeed.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });

        imgTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new TranslateNewFeedFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_URL_NEW_FEED, mViewModel.getUrlNewFeed());
                fragment.setArguments(bundle);

                ft.add(R.id.layoutPageNewFeedContainer, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


    }
}
