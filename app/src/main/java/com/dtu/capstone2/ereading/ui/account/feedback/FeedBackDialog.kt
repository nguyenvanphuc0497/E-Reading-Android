package com.dtu.capstone2.ereading.ui.account.feedback

import android.app.Dialog
import android.widget.Button
import android.widget.EditText
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.ui.utils.BaseDialog

/**
 * Create by Nguyen Van Phuc on 19/5/19
 */
class FeedBackDialog : BaseDialog() {
    private lateinit var btnSubmit: Button

    internal lateinit var edtMessage: EditText
    internal lateinit var edtContact: EditText
    internal var onSubmitClicked: () -> Unit = {}

    override fun setContentDialog(dialog: Dialog) {
        dialog.setContentView(R.layout.dialog_feedback)
        dialog.apply {
            setCancelable(true)
            setCanceledOnTouchOutside(true)
        }
    }

    override fun initListeners(dialog: Dialog) {
        edtMessage = dialog.findViewById(R.id.edt_feedback_message)
        edtContact = dialog.findViewById(R.id.edt_feedback_contact)
        btnSubmit = dialog.findViewById(R.id.btn_feedback_submit)
        btnSubmit.setOnClickListener {
            onSubmitClicked()
        }
    }
}
