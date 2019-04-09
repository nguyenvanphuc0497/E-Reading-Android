package com.dtu.capstone2.ereading.ui.utils

import android.content.Context
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
abstract class BaseFragment : Fragment() {
    private var nameActivity = "null"
    protected val managerSubscribe: CompositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        nameActivity = activity?.javaClass?.simpleName ?: "null"
    }

    override fun onPause() {
        super.onPause()
        managerSubscribe.clear()
    }

    protected fun showLoadingDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_LOADING, nameActivity))
    }

    protected fun dismissLoadingDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DISMISS_DIALOG_LOADING, nameActivity))
    }

    protected fun showApiErrorDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_API_ERROR, nameActivity))
    }

    protected fun showSuccessDialog() {
        RxBusTransport.publish(Transport(TypeTransportBus.DIALOG_SUCCESS, nameActivity))
    }
}
