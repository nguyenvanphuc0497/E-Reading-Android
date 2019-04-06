package com.dtu.capstone2.ereading.ui.newfeed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.model.ItemPageNewFeed;

import java.util.List;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
public class PageNewFeedAdapter extends RecyclerView.Adapter<PageNewFeedAdapter.PageNewFeedViewHolder> {
    private List<ItemPageNewFeed> mItemPageNewFeeds;
    private OnItemListener mOnItemListener;

    PageNewFeedAdapter(List<ItemPageNewFeed> itemPageNewFeeds) {
        mItemPageNewFeeds = itemPageNewFeeds;
    }

    public void setmItemPageNewFeeds(OnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public PageNewFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PageNewFeedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_page_new_feed, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PageNewFeedViewHolder pageNewFeedViewHolder, int i) {
        pageNewFeedViewHolder.onBindData(i);
    }

    @Override
    public int getItemCount() {
        return mItemPageNewFeeds.size();
    }

    /**
     * Class is view holder of this adapter
     */
    class PageNewFeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView titleSourceFeed;

        PageNewFeedViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLogo = itemView.findViewById(R.id.imgPageNewFeedLogo);
            titleSourceFeed = itemView.findViewById(R.id.tvPageNewFeedTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemListener.onItemClick(getAdapterPosition());
                }
            });
        }

        void onBindData(int position) {
            titleSourceFeed.setText(mItemPageNewFeeds.get(position).getTitleSourceFeed());
        }
    }

    /**
     * Interface is used listener event of item
     */
    interface OnItemListener {
        void onItemClick(int position);
    }
}
