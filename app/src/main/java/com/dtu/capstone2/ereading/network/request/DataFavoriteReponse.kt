package com.dtu.capstone2.ereading.network.request

import com.google.gson.annotations.SerializedName

/**
 * Create by Vo The Doan on 04/30/2019
 */
data class DataFavoriteReponse(@SerializedName("data") val listData: ArrayList<listFavorite> )

data class listFavorite (@SerializedName ("strData") val strData: String)