package com.dtu.capstone2.ereading.ui.utils

import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * Create by Nguyen Van Phuc on 2019-04-22
 */
class FavoriteWordClickableSpan : ClickableSpan() {
    internal val TAG = this.javaClass.simpleName

    override fun onClick(widget: View) {
        with(widget as AppCompatTextView) {
            RxBusTransport.publish(Transport(TypeTransportBus.SPAN_ON_CLICK, TAG, this.text.substring(this.selectionStart, this.selectionEnd)))
        }
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = Color.BLUE
        ds.isUnderlineText = false
    }
}
