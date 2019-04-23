package com.dtu.capstone2.ereading.ui.utils

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View

/**
 * Create by Nguyen Van Phuc on 2019-04-22
 */
class BaseClickableSpan : ClickableSpan() {
    override fun onClick(widget: View) {
        Log.e("BaseClickableSpan", "click")
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = Color.BLUE
        ds.isUnderlineText = false
    }
}
