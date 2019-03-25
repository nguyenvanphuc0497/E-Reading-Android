package com.dtu.capstone2.ereading.ui.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.displayanews.DisplayANewsFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Create By Huynh Vu Ha Lan on 20/03/2019
 */
public class NewsFragment extends Fragment {

    private List<News> newsList;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        //Display recycler view
        recyclerView = view.findViewById(R.id.rcw_news);
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(newsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvent();

    }

    private void addEvent() {
        createData();//Call method add data
        newsAdapter.notifyDataSetChanged();

        newsAdapter.setItemClickListener(new ItemRecyclerViewClickListener() {
            @Override
            public void onClick(int position) {
                Fragment newDetails = new DisplayANewsFragment();
                FragmentTransaction displayANewsFragment = getActivity().getSupportFragmentManager().beginTransaction();
                displayANewsFragment.replace(R.id.news_fragment, newDetails);
                displayANewsFragment.addToBackStack(null);
                displayANewsFragment.commit();

                Toast.makeText(getContext(), "day la item thu " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    //Add data to newsList
    public void createData() {
        News news = new News("Doanh nghiệp Việt đối phó với các cuộc tấn công qua email như thế nào?", R.drawable.news3);
        newsList.add(news);

        news = new News("Qualcomm ra mắt thiết kế kính VR mới: hoạt động độc lập và có thể kết nối không dây với PC", R.drawable.news2);
        newsList.add(news);

        news = new News("Microsoft mang ứng dụng Android lên Windows 10 bằng phần mềm...ánh xạ màn hình vừa ra mắt bản beta", R.drawable.news1);
        newsList.add(news);
    }
}
