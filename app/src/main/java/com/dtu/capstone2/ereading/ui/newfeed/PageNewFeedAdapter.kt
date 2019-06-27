package com.dtu.capstone2.ereading.ui.newfeed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.model.ItemPageNewFeed
import com.dtu.capstone2.ereading.ui.utils.glideLoadImage
import kotlinx.android.synthetic.main.item_page_new_feed.view.*

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
class PageNewFeedAdapter internal constructor(private val mItemPageNewFeeds: List<ItemPageNewFeed>) : RecyclerView.Adapter<PageNewFeedAdapter.PageNewFeedViewHolder>() {
    internal var onItemClick: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PageNewFeedViewHolder {
        return PageNewFeedViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_page_new_feed, viewGroup, false))
    }

    override fun onBindViewHolder(pageNewFeedViewHolder: PageNewFeedViewHolder, i: Int) {
        pageNewFeedViewHolder.onBindData(mItemPageNewFeeds[i])
    }

    override fun getItemCount(): Int {
        return mItemPageNewFeeds.size
    }

    /**
     * Class is view holder of this adapter
     */
    inner class PageNewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onItemClick(adapterPosition) }
        }

        fun onBindData(itemPageNewFeed: ItemPageNewFeed) {
            itemView.also {
                it.tvPageNewFeedTitle.text = itemPageNewFeed.titleSourceFeed
                it.imgPageNewFeedLogo.glideLoadImage(itemPageNewFeed.urlImageLogo)
            }
        }
    }
}
