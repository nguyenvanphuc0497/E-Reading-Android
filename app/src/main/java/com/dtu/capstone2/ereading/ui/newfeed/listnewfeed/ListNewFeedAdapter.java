package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.response.RssItemResponse;

import java.util.List;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
public class ListNewFeedAdapter extends RecyclerView.Adapter<ListNewFeedAdapter.ListNewFeedViewHolder> {
    private List<RssItemResponse> mRssItemResponses;
    private Context mContext;

    ListNewFeedAdapter(List<RssItemResponse> rssItemResponses, Context context) {
        mRssItemResponses = rssItemResponses;
        mContext = context;
    }

    @NonNull
    @Override
    public ListNewFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_news_feed, viewGroup, false);

        return new ListNewFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewFeedViewHolder listNewFeedViewHolder, int i) {
        listNewFeedViewHolder.onBindData(mRssItemResponses.get(i));
    }

    @Override
    public int getItemCount() {
        return mRssItemResponses.size();
    }

    /**
     * Class is view holder view for adapter
     */
    class ListNewFeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgNewsThumbnail;
        private TextView tvNewsTitle;
        private TextView tvNewsDescription;

        //Setting các tuỳ chọn cho thư viện load ảnh Glide
        private RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_image_thumbnail_default)
                .error(R.drawable.ic_thumbnail_error);

        ListNewFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNewsThumbnail = itemView.findViewById(R.id.img_news_thumbnail);
            tvNewsTitle = itemView.findViewById(R.id.tv_news_title);
            tvNewsDescription = itemView.findViewById(R.id.tv_news_description);
        }

        void onBindData(RssItemResponse rssItemResponse) {
            String urlImage = "";
            if (!rssItemResponse.getRssItemMediaGroups().isEmpty()) {
                urlImage = rssItemResponse.getRssItemMediaGroups().get(0).getUrlImage();
            }

            //Load Anh tu Url sử dụng thư viện Glide
            Glide.with(mContext).load(urlImage).apply(options).into(imgNewsThumbnail);
            tvNewsTitle.setText(rssItemResponse.getTitle());
            tvNewsDescription.setText(rssItemResponse.getDescription());

        }
    }
}
