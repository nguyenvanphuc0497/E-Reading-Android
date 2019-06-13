package com.dtu.capstone2.ereading.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Create by Nguyen Van Phuc on 4/8/19
 */
@Parcelize
data class ItemListNewFeedPager(val titleFragment: String,
                                val urlEndPointRSS: String) : Parcelable
