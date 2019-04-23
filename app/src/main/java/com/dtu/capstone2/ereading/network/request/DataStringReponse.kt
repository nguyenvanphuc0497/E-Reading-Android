package com.dtu.capstone2.ereading.network.request

import com.google.gson.annotations.SerializedName

/**
 * Create by Vo The Doan on 3/22/2019
 */
data class DataStringReponse(@SerializedName("text") val stringData: String,
                             @SerializedName("listWords") val listVocabulary: List<Vocabulary>)

data class Vocabulary(@SerializedName("id") val idVocabulary: Int,
                      val word: String,
                      val type: String,
                      @SerializedName("start_index") val startIndex: Int,
                      @SerializedName("end_index") val endIndex: Int)
