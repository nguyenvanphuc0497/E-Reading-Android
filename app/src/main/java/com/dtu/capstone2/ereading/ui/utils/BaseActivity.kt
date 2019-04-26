package com.dtu.capstone2.ereading.ui.utils

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.disposables.CompositeDisposable

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
abstract class BaseActivity : AppCompatActivity() {
    companion object {
        const val TIME_DELAY_DISMISS_DIALOG_SUCCESS = 1300L
    }

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var apiErrorDialog: ApiErrorDialog
    private lateinit var successDialog: SuccessDialog
    private lateinit var errorDialog: ErrorDialog
    private val managerSubscribe: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog()
        apiErrorDialog = ApiErrorDialog()
        successDialog = SuccessDialog()
        errorDialog = ErrorDialog()

    }

    override fun onResume() {
        super.onResume()
        initListener()
    }

    override fun onPause() {
        super.onPause()
        managerSubscribe.clear()
    }

    private fun initListener() {
        managerSubscribe.add(RxBusTransport.listen().observeOnUiThread().subscribe({
            when (it.typeTransport) {
                TypeTransportBus.DIALOG_LOADING -> {
                    loadingDialog.show(supportFragmentManager, TypeTransportBus.DIALOG_LOADING.typeValue)
                }
                TypeTransportBus.DIALOG_API_ERROR -> {
                    loadingDialog.dismiss()
                    apiErrorDialog.show(supportFragmentManager, TypeTransportBus.DIALOG_API_ERROR.typeValue)
                }
                TypeTransportBus.DIALOG_SUCCESS -> {
                    loadingDialog.dismiss()
                    successDialog.show(supportFragmentManager, TypeTransportBus.DIALOG_SUCCESS.typeValue)
                    Handler().postDelayed({
                        successDialog.dismiss()
                    }, TIME_DELAY_DISMISS_DIALOG_SUCCESS)
                }
                TypeTransportBus.DISMISS_DIALOG_LOADING -> {
                    loadingDialog.dismiss()
                }
                TypeTransportBus.DIALOG_ERROR_MESSAGE -> {
                    loadingDialog.dismiss()
                    errorDialog.titleDialog = it.message
                    errorDialog.show(supportFragmentManager, TypeTransportBus.DIALOG_API_ERROR.typeValue)
                }
            }
        }, {
            Log.w("BaseActivity", ":$it")
        }))
    }
}
