package com.dtu.capstone2.ereading.ui.utils

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
abstract class BaseFragment : Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var apiErrorDialog: ApiErrorDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog()
        apiErrorDialog = ApiErrorDialog()
    }

    protected fun showLoadingDialog(tag: String) {
        loadingDialog.show(fragmentManager, tag)
    }

    protected fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    protected fun showApiErrorDialog(tag: String) {
        apiErrorDialog.show(fragmentManager, tag)
    }
}
