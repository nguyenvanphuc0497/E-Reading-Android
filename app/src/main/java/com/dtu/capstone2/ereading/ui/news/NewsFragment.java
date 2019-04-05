package com.dtu.capstone2.ereading.ui.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.Interface.ItemRecyclerViewClickListener;
import com.dtu.capstone2.ereading.ui.adapter.FeedAdapter;
import com.dtu.capstone2.ereading.ui.adapter.NewsAdapter;
import com.dtu.capstone2.ereading.ui.common.HTTPDataHandler;
import com.dtu.capstone2.ereading.ui.displayanews.DisplayANewsFragment;
import com.dtu.capstone2.ereading.ui.model.Feed;
import com.dtu.capstone2.ereading.ui.model.RSSObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private List<Feed> feedList;
    private RecyclerView recyclerView;
    private FeedAdapter feedAdapter;
    private RSSObject rssObject;

    private final String rssLink = "http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
    private final String rssToJSonAPI = "https://api.rss2json.com/v1/api.json?rss_url";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.rcw_news);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(feedAdapter);

        loadRSS();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void loadRSS() {
        AsyncTask<String, String, String> loadAsync = new AsyncTask<String, String, String>() {


            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.getHTTPData(params[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                rssObject =new Gson().fromJson(s, RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject, getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        StringBuilder urlGetData = new StringBuilder(rssToJSonAPI);
        urlGetData.append(rssLink);
        loadAsync.execute(urlGetData.toString());
    }

}
