package com.dtu.capstone2.ereading.ui.utils

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    protected fun showLoadingDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_LOADING, "phuc"))
    }

    protected fun dismissLoadingDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DISMISS_DIALOG_LOADING, "phuc"))
    }

    protected fun showApiErrorDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_API_ERROR, "phuc"))
    }

    protected fun showSuccessDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_SUCCESS, "phuc"))

    }

//    protected fun setCallBakSuccessDialogDismiss(callBack: () -> Unit) {
//        successDialog.onDismissCallback = callBack
//    }
}
