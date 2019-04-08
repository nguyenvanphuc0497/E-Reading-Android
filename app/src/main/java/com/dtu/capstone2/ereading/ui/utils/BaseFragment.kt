package com.dtu.capstone2.ereading.ui.utils

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
abstract class BaseFragment : Fragment() {
    companion object {
        const val TIME_DELAY_DISMISSS_DIALOG_SUCCESS = 1300L
    }

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var apiErrorDialog: ApiErrorDialog
    private lateinit var successDialog: SuccessDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog()
        apiErrorDialog = ApiErrorDialog()
        successDialog = SuccessDialog()
    }

    protected fun showLoadingDialog() {
        loadingDialog.show(fragmentManager, null)
    }

    protected fun dismissLoadingDialog() {
        if (loadingDialog.isVisible) {
            loadingDialog.dismiss()
        }
    }

    protected fun showApiErrorDialog() {
        dismissLoadingDialog()
        if (!apiErrorDialog.isVisible) {
            apiErrorDialog.show(fragmentManager, null)
        }
    }

    protected fun showSuccessDialog() {
        dismissLoadingDialog()
        if (!successDialog.isVisible) {
            successDialog.show(fragmentManager, null)
            Handler().postDelayed({
                successDialog.dismiss()
            }, TIME_DELAY_DISMISSS_DIALOG_SUCCESS)
        }
    }

    protected fun setCallBakSuccessDialogDismiss(callBack: () -> Unit) {
        successDialog.onDismissCallback = callBack
    }
}
