package com.dtu.capstone2.ereading.ui.utils

import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
object RxBusTransport {
    private val publisher = PublishSubject.create<Transport>()

    /**
     * Emit item to listen
     */
    fun publish(transport: Transport) {
        Log.i("RxBusTransport", "$transport")
        publisher.onNext(transport)
    }

    /**
     * Listen should return an Observable and not the publisher
     * Using ofType we filter only events that match that class type
     */
    fun listen(): Observable<Transport> {
        Log.i("RxBusTransport", "listen")
        return publisher.ofType(Transport::class.java)
    }
}

data class Transport(val typeTransport: TypeTransportBus, val sender: String = "", val message: String = "")

enum class TypeTransportBus(val typeValue: String) {
    DIALOG_LOADING("dialog_loading"),
    DISMISS_DIALOG_LOADING("dismiss_dialog_loading"),
    DIALOG_API_ERROR("dialog_api_error"),
    DIALOG_SUCCESS("dialog_success"),
    CALL_BACK_DIALOG_SUCCESS_DISMISS("call_back_dialog_success_dismiss"),
    DIALOG_ERROR_MESSAGE("dialog_error_message"),
    CALL_BACK_DIALOG_ERROR_MESSAGE("call_back_dialog_error_message"),
    SPAN_ON_CLICK("span_on_click"),
}
