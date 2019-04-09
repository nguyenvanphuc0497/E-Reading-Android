package com.dtu.capstone2.ereading.network.request

import com.google.gson.annotations.SerializedName

/**
 * Create by Vo The Doan on 4/1/2019
 */
data class DataLoginRequest(@SerializedName("token") val stringToken: String,
                            @SerializedName("user_id") val intId: Int,
                            @SerializedName("email") val stringEmail: String)
