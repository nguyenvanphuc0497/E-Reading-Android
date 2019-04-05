package com.dtu.capstone2.ereading.ui.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.Interface.ItemClickListener;
import com.dtu.capstone2.ereading.ui.model.Feed;
import com.dtu.capstone2.ereading.ui.model.Item;
import com.dtu.capstone2.ereading.ui.model.RSSObject;
import com.squareup.picasso.Picasso;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView tvTitle;
    public ImageView ivThumbnail;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_news_title);
        ivThumbnail = itemView.findViewById(R.id.btn_news_thumbnail);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public Context getmContext() {
        return mContext;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_news_recyclerview,parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.tvTitle.setText(rssObject.getItems().get(position).getTitle());
    }

    public int getItemCount() {
        return rssObject.items.size();
    }
}