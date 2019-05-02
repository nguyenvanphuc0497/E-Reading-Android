package com.dtu.capstone2.ereading.ui.utils

import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.dtu.capstone2.ereading.ui.model.VocabularyLocation

/**
 * Create by Nguyen Van Phuc on 2019-04-22
 */
class DefaultWordClickableSpan : ClickableSpan() {
    private val TAG = this.javaClass.simpleName

    override fun onClick(widget: View) {
        with(widget as AppCompatTextView) {
            RxBusTransport.publish(Transport(TypeTransportBus.SPAN_ON_CLICK, TAG, VocabularyLocation((this.tag as? Int?)
                    ?: -1, this.selectionStart, this.selectionEnd)))
            this.text = setOrRemoveHighLightText(this)
        }
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
    }

    private fun setOrRemoveHighLightText(appCompatTextView: AppCompatTextView): Spannable {
        val spannable = SpannableString(appCompatTextView.text)
        val foregroundColorSpan: Array<ForegroundColorSpan> = spannable.getSpans(appCompatTextView.selectionStart, appCompatTextView.selectionEnd, ForegroundColorSpan::class.java)
        if (foregroundColorSpan.isNotEmpty()) {
            foregroundColorSpan.forEach {
                spannable.removeSpan(it)
            }
        } else {
            spannable.setSpan(ForegroundColorSpan(Color.GREEN), appCompatTextView.selectionStart, appCompatTextView.selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannable
    }
}
