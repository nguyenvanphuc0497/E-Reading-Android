package com.dtu.capstone2.ereading.network.request

import com.google.gson.annotations.SerializedName

/**
 * Create by Vo The Doan on 3/22/2019
 */
data class DataStringReponse(@SerializedName("text") val stringData: String,
                             @SerializedName("listWords") val listWord: List<ListVocabulary>)

data class ListVocabulary(@SerializedName("id") val intID: Int,
                          @SerializedName("word") val stringWord: String,
                          @SerializedName("type") val stringType: String)
