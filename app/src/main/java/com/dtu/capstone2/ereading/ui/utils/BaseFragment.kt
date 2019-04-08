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
    private var loadingDialogIsShowing = false
    private var apiErrorDialogIsShowing = false
    private var successDialogDialogIsShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog()
        apiErrorDialog = ApiErrorDialog()
        successDialog = SuccessDialog()
    }

    protected fun showLoadingDialog() {
        if (!loadingDialogIsShowing) {
            loadingDialog.show(fragmentManager, null)
            loadingDialogIsShowing = true
        }
    }

    protected fun dismissLoadingDialog() {
        if (loadingDialogIsShowing) {
            loadingDialog.dismiss()
            loadingDialogIsShowing = false
        }
    }

    protected fun showApiErrorDialog() {
        dismissLoadingDialog()
        if (!apiErrorDialogIsShowing) {
            apiErrorDialog.show(fragmentManager, null)
            apiErrorDialogIsShowing = true
        }
    }

    protected fun showSuccessDialog() {
        dismissLoadingDialog()
        if (!successDialogDialogIsShowing) {
            successDialog.show(fragmentManager, null)
            successDialogDialogIsShowing = false
            Handler().postDelayed({
                if (successDialogDialogIsShowing) {
                    successDialog.dismiss()
                    successDialogDialogIsShowing = false
                }
            }, TIME_DELAY_DISMISSS_DIALOG_SUCCESS)
        }
    }

    protected fun setCallBakSuccessDialogDismiss(callBack: () -> Unit) {
        successDialog.onDismissCallback = callBack
    }
}
