package com.dtu.capstone2.ereading.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
