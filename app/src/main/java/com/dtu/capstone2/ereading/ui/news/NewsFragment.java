package com.dtu.capstone2.ereading.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvOpenNews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        tvOpenNews = view.findViewById(R.id.tvNewsOpen);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvOpenNews.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvNewsOpen) {
            startActivity(new Intent(getActivity(), NewsContainerActivity.class));
        }
    }
}
