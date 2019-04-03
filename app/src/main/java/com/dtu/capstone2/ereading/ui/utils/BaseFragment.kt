package com.dtu.capstone2.ereading.ui.utils

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
abstract class BaseFragment : Fragment() {
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog()
    }

    protected fun showLoadingDialog(tag: String) {
        loadingDialog.show(fragmentManager, tag)
    }

    protected fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }
}
