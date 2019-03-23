package com.dtu.capstone2.ereadingandroid.network.request

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
data class AccountLoginRequest(@SerializedName("username") val userName: String,
                               @SerializedName("password") val password: String)



