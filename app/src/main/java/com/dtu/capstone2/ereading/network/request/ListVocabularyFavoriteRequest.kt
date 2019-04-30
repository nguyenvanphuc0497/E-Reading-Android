package com.dtu.capstone2.ereading.network.request

import com.google.gson.annotations.SerializedName

/**
 * Create by Nguyen Van Phuc on 2019-04-30
 */
data class ListVocabularyFavoriteRequest(val data: List<VocabularyFavoriteRequest>)

data class VocabularyFavoriteRequest(val id: Int,
                                     @SerializedName("vocabulary_word") val word: String,
                                     @SerializedName("type_vocabulary") val type: String)
