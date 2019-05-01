package com.dtu.capstone2.ereading.ui.utils

import android.text.Spannable
import android.text.SpannableString
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
internal fun <T> Observable<T>.observeOnUiThread(): Observable<T> =
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

internal fun <T> Single<T>.observeOnUiThread(): Single<T> =
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

internal fun <T> Single<T>.publishDialogLoading(): Single<T> =
        this.doOnSubscribe {
            RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_LOADING))
        }.doOnSuccess {
            RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_SUCCESS))
        }.doOnError {
            (it as? HttpException)?.let { exception ->
                if (exception.code() >= HttpsURLConnection.HTTP_INTERNAL_ERROR) {
                    RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_API_ERROR))
                }
            }
        }

internal fun LineContentNewFeed.setSpannerEvent(): SpannableString {
    val result = SpannableString(this.textContent)
    this.listVocabularies?.forEach {
        result.setSpan(FavoriteWordClickableSpan(), it.startIndex, it.endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.listVocabulariesNotTranslate?.forEach {
        result.setSpan(DefaultWordClickableSpan(), it.startIndex, it.endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return result
}
