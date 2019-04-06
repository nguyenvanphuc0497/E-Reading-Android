package com.dtu.capstone2.ereading.ui.newfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

/**
 * Create By Huynh Vu Ha Lan on 21/03/2019
 */
public class NewFeedDisplayFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_new_feed_display, container, false);
        return view;
    }
}
