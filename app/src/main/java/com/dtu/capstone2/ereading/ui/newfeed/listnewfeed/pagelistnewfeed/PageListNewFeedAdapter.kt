package com.dtu.capstone2.ereading.ui.newfeed.listnewfeed.pagelistnewfeed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.network.response.BBCRssItemResponse
import kotlinx.android.synthetic.main.item_list_news_feed.view.*

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
class PageListNewFeedAdapter(private val mRssItemResponses: List<BBCRssItemResponse>) :
        RecyclerView.Adapter<PageListNewFeedAdapter.ListNewFeedViewHolder>() {
    internal var onItemClick: (position: Int) -> Unit = { _ -> }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListNewFeedViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_list_news_feed, viewGroup, false)

        return ListNewFeedViewHolder(view)
    }

    override fun onBindViewHolder(listNewFeedViewHolder: ListNewFeedViewHolder, i: Int) {
        listNewFeedViewHolder.onBindData(mRssItemResponses[i])
    }

    override fun getItemCount(): Int {
        return mRssItemResponses.size
    }

    /**
     * Class is view holder view for adapter
     */
    inner class ListNewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onItemClick(adapterPosition) }
        }

        //Setting các tuỳ chọn cho thư viện load ảnh Glide
        private val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_image_thumbnail_default)
                .error(R.drawable.ic_thumbnail_error)

        internal fun onBindData(rssItemResponse: BBCRssItemResponse) {
            //Load Anh tu Url sử dụng thư viện Glide
            itemView.also {
                Glide.with(itemView.context).load(rssItemResponse.bbcRssThumbnail.urlImage)
                        .apply(options)
                        .into(it.img_news_thumbnail)
                it.tv_news_title.text = rssItemResponse.title
                it.tv_news_description.text = rssItemResponse.description
                it.tv_news_time_publish.text = rssItemResponse.pushDate
            }
        }
    }
}
