package com.dtu.capstone2.ereadingandroid.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dtu.capstone2.ereadingandroid.R;
import com.dtu.capstone2.ereadingandroid.helper.ItemRecyclerViewClickListener;

import java.util.List;

/**
 * Create By Huynh Vu Ha Lan on 19/03/2019
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    /** Create ViewHolder*/
    private ItemRecyclerViewClickListener itemClickListener;

    public class NewsViewHolder extends  RecyclerView.ViewHolder{
        private TextView title;
        private ImageButton thumbnail;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tw_news_title);
            thumbnail = itemView.findViewById(R.id.btn_news_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(getAdapterPosition());
                }
            });
        }



    }

    private List<News> newsList;
    private Fragment fragment;

    /**Constructor*/
    public NewsAdapter(Fragment fragment, List<News> newsList) {
        this.fragment= fragment;
        this.newsList = newsList;
    }

    //tao setter cho bien itemClickListener
    public void setItemClickListener(ItemRecyclerViewClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /** Get layout */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_recyclerview,parent,false);
        return new NewsViewHolder(view);
    }

    FragmentManager fm;

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        /** Set Value*/
        final News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.thumbnail.setBackgroundResource(news.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
