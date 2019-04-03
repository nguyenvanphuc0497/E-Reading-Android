package com.dtu.capstone2.ereading.ui.utils

import android.app.Dialog
import android.content.DialogInterface
import com.dtu.capstone2.ereading.R

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
class SuccessDialog : BaseDialog() {

    internal var onDismissCallback: () -> Unit = {}

    override fun setContentDialog(dialog: Dialog) {
        dialog.setContentView(R.layout.dialog_success)
    }

    override fun initListeners(dialog: Dialog) {}

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onDismissCallback()
    }
}
