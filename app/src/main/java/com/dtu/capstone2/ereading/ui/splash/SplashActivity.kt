package com.dtu.capstone2.ereading.ui.splash

import android.content.Intent
import android.os.Bundle
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import com.dtu.capstone2.ereading.ui.MainActivity
import com.dtu.capstone2.ereading.ui.utils.BaseActivity
import com.dtu.capstone2.ereading.ui.utils.observeOnUiThread

/**
 * Create by Nguyen Van Phuc on 2019-06-13
 */
class SplashActivity : BaseActivity() {
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initData()
        initEvent()
    }

    private fun initData() {
        viewModel = SplashViewModel(LocalRepository(this))
        viewModel.loadSettingApplicationFromServer()
    }

    private fun initEvent() {
        viewModel.singleObserver.observeOnUiThread()
                .subscribe({
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(R.animator.anim_slide_new_in_right, R.animator.anim_slide_old_out_left)
                }, {})
    }
}
