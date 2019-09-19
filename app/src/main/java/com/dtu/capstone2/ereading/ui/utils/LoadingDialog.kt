package com.dtu.capstone2.ereading.ui.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dtu.capstone2.ereading.R
import kotlinx.android.synthetic.main.dialog_loading.*

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
class LoadingDialog : DialogFragment() {
    private var isShowing = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(context) {
            override fun onBackPressed() {
                //disable onBack dismiss dialog
            }
        }.apply {
            window?.apply {
                requestFeature(Window.FEATURE_NO_TITLE)
                setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setDimAmount(0f)
            }
            setContentView(R.layout.dialog_loading)
            progressLoadingMain?.indeterminateDrawable?.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorBackgroundMainApp), PorterDuff.Mode.SRC_IN)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isShowing) {
            isShowing = true
            super.show(manager, tag)
        }
    }

    override fun dismiss() {
        if (isShowing) {
            isShowing = false
            super.dismiss()
        }
    }
}
