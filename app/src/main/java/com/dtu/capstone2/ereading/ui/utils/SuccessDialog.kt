package com.dtu.capstone2.ereading.ui.utils

import android.app.Dialog
import android.content.DialogInterface
import android.support.v4.app.FragmentManager
import com.dtu.capstone2.ereading.R

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
class SuccessDialog : BaseDialog() {
    companion object {
        private var isShowing = false
    }

    internal var onDismissCallback: () -> Unit = {}

    override fun setContentDialog(dialog: Dialog) {
        dialog.setContentView(R.layout.dialog_success)
    }

    override fun initListeners(dialog: Dialog) {}

    override fun show(manager: FragmentManager?, tag: String?) {
        if (!isShowing) {
            isShowing = true
            super.show(manager, tag)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDismissCallback()
    }

    override fun dismiss() {
        if (isShowing) {
            isShowing = false
            super.onDismiss(dialog)
        }
    }
}
