package com.dtu.capstone2.ereading.ui.account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import com.dtu.capstone2.ereading.network.utils.ApiExceptionResponse
import com.dtu.capstone2.ereading.ui.account.favorite.FavoriteFragment
import com.dtu.capstone2.ereading.ui.account.history.HistoryFragment
import com.dtu.capstone2.ereading.ui.model.ErrorUnauthorizedRespone
import com.dtu.capstone2.ereading.ui.utils.BaseFragment
import com.dtu.capstone2.ereading.ui.utils.observeOnUiThread
import com.google.gson.Gson
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_manager.*
import javax.net.ssl.HttpsURLConnection

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
class PageAccountFragment : BaseFragment() {
    private lateinit var mViewModel: PageAccountViewModel
    private lateinit var builder: AlertDialog.Builder
    private var mItemSelect = -1

    override fun initData() {
        mViewModel = PageAccountViewModel(EReadingRepository(), LocalRepository(context!!))
        initDialog()
    }

    override fun initView(view: View?) {

    }

    override fun initEvent() {
        layout_manager_logo_login?.setOnClickListener {
            startActivity(Intent(context, ManagerAccountContainerActivity::class.java))
            activity!!.overridePendingTransition(R.animator.anim_slide_new_in_right, R.animator.anim_slide_old_out_left)
        }
        tv_manager_logout?.setOnClickListener {
            if (mViewModel.isLogin!!) {
                val dialog = AlertDialog.Builder(context!!)
                dialog.setCancelable(false)
                dialog.setTitle("Thông báo!")
                dialog.setMessage("Bạn có muốn đăng xuất?")
                dialog.setPositiveButton("Đăng xuất") { _, _ ->
                    mViewModel.logOut()
                    loadInfoLoginToView()
                }
                        .setNegativeButton("Hủy") { _, _ ->
                            //Action for "Cancel".
                        }

                val alert = dialog.create()
                alert.show()
            } else {
                showToastRequirementLogin("")
            }
        }
        tv_manager_favorite?.setOnClickListener {
            if (mViewModel.isLogin!!) {
                replaceFragment(R.id.layoutPageAccountContainer, FavoriteFragment())
            } else {
                showToastRequirementLogin("")
            }
        }
        tv_manager_level_english?.setOnClickListener(View.OnClickListener {
            if (!mViewModel.isLogin) {
                showToastRequirementLogin("")
                return@OnClickListener
            }
            showLoadingDialog()
            mViewModel.listLevelFromServer
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : SingleObserver<List<String>> {
                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onSuccess(strings: List<String>) {
                            dismissLoadingDialog()
                            showDialog(strings.toTypedArray(), mViewModel.levelSelected)
                        }

                        override fun onError(e: Throwable) {
                            val response = e as ApiExceptionResponse
                            if (response.statusCode != null && response.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                                val gson = Gson()
                                val (detail) = gson.fromJson(response.messageError, ErrorUnauthorizedRespone::class.java)
                                showToastRequirementLogin(detail)
                            } else {
                                showApiErrorDialog()
                            }
                        }
                    })
        })

        tv_manager_history?.setOnClickListener {
            if (mViewModel.isLogin) {
                replaceFragment(R.id.layoutPageAccountContainer, HistoryFragment())
            } else {
                showToastRequirementLogin("")
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_page_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    override fun onResume() {
        super.onResume()
        loadInfoLoginToView()
    }

    private fun initDialog() {
        builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Trình độ tiếng anh của bạn ?")
    }

    private fun showDialog(arrayNameLevel: Array<String>, levelSelected: Int) {
        mItemSelect = -1
        builder.setSingleChoiceItems(arrayNameLevel, levelSelected) { _, i -> mItemSelect = i }
        builder.setPositiveButton("OK") { _, _ ->
            if (mItemSelect != -1) {
                handelEventSetLevelUserToServer(mItemSelect)
            }
            mItemSelect = -1
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun loadInfoLoginToView() {
        if (mViewModel.isLogin) {
            tv_page_account_manager_email_user?.text = mViewModel.emailFromLocal
            layout_manager_logo_login?.isEnabled = false
            tv_manager_logout?.visibility = View.VISIBLE
        } else {
            tv_page_account_manager_email_user.text = getString(R.string.page_account_login_info_default)
            layout_manager_logo_login?.isEnabled = true
            tv_manager_logout?.visibility = View.GONE
        }
    }

    private fun handelEventSetLevelUserToServer(position: Int) {
        showLoadingDialog()
        managerSubscribe.add(mViewModel.setLevelOfUserToServer(position)
                .observeOnUiThread()
                .subscribe({
                    showSuccessDialog("", true)
                }, {
                    showApiErrorDialog()
                }))
    }
}
