package com.dtu.capstone2.ereading.ui.newfeed.translate

import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed
import com.dtu.capstone2.ereading.ui.utils.DefaultWordClickableSpan
import com.dtu.capstone2.ereading.ui.utils.FavoriteWordClickableSpan
import kotlinx.android.synthetic.main.item_translate_result_content.view.*

/**
 * Create by Nguyen Van Phuc on 2019-05-01
 */
class TranslateNewFeedAdapter(private val data: List<LineContentNewFeed>) : RecyclerView.Adapter<TranslateNewFeedAdapter.TranslateNewFeedViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, itemType: Int): TranslateNewFeedViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_translate_result_content, container, false)
        return TranslateNewFeedViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(viewHolder: TranslateNewFeedViewHolder, position: Int) {
        viewHolder.onBindData(data[position])
    }

    inner class TranslateNewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tvTranslateNewFeedWordResultContent?.movementMethod = LinkMovementMethod.getInstance()
        }

        fun onBindData(newFeed: LineContentNewFeed) {
            itemView.tvTranslateNewFeedWordResultContent?.text = setSpannableClick(newFeed)
        }

        private fun setSpannableClick(contentNewFeed: LineContentNewFeed): SpannableString {
            val result = SpannableString(contentNewFeed.textContent)
            if (contentNewFeed.listVocabularies != null && !contentNewFeed.listVocabularies.isEmpty()) {
                for ((_, _, _, startIndex, endIndex) in contentNewFeed.listVocabularies) {
                    result.setSpan(FavoriteWordClickableSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
            if (contentNewFeed.listVocabulariesNotTranslate != null && !contentNewFeed.listVocabulariesNotTranslate.isEmpty()) {
                for ((_, _, _, startIndex, endIndex) in contentNewFeed.listVocabulariesNotTranslate) {
                    result.setSpan(DefaultWordClickableSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
            return result
        }
    }
}
