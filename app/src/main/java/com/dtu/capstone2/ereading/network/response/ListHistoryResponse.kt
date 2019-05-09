package com.dtu.capstone2.ereading.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
data class ListHistoryResponse(@SerializedName("history_new_feed") val listData: ArrayList<HistoryNewFeed>)

data class HistoryNewFeed(@SerializedName("title_new_feed") val titleNewsFeed: String,
                          @SerializedName("introduction_new_feed") val introduction: String)
