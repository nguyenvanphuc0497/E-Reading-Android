package com.dtu.capstone2.ereading.ui.utils

import android.os.Bundle
import android.support.v7.util.DiffUtil
import com.dtu.capstone2.ereading.network.response.HistoryNewFeed


/**
 * Create by Nguyen Van Phuc on 2019-05-10
 */
class BaseDIffUtilCallBack(private val newList: List<HistoryNewFeed>, private val oldList: List<HistoryNewFeed>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val result = newList[newItemPosition].compareTo(oldList[oldItemPosition])
        return result == 0
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newContact = newList[newItemPosition]
        val oldContact = oldList[oldItemPosition]

        val diff = Bundle()
        if (newContact.introduction != oldContact.introduction) {
            diff.putString("introduction", newContact.introduction)
        }
        if (newContact.timeCreate != oldContact.timeCreate) {
            diff.putString("timeCreate", newContact.timeCreate)
        }
        if (newContact.titleNewsFeed != oldContact.titleNewsFeed) {
            diff.putString("titleNewsFeed", newContact.titleNewsFeed)
        }
        return if (diff.size() == 0) {
            null
        } else diff
    }
}
