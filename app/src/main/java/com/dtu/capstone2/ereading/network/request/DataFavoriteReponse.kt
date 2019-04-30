package com.dtu.capstone2.ereading.network.request

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Create by Vo The Doan on 04/30/2019
 */
data class DataFavoriteReponse(@SerializedName("vocabulary_favorite") val listData: ArrayList<listFavorite> )

data class listFavorite (@SerializedName ("id") val intId: Int,
                         @SerializedName("time_create") val dateCreate: Date,
                         @SerializedName( "is_hard") val isHard: Boolean,
                         @SerializedName ("vocabulary_id") val intIdVocabulary : Int,
                         @SerializedName("vocabulary_word") val strWord: String,
                         @SerializedName("vocabulary_mean_short") val strMeanShort: String,
                         @SerializedName("type_vocabulary") val strType: String)