package com.dtu.capstone2.ereading.ui.splash

import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.subjects.SingleSubject

/**
 * Create by Nguyen Van Phuc on 2019-06-13
 */
class SplashViewModel(private val localRepository: LocalRepository) {
    companion object {
        private const val REMOTE_CONFIG_KEY_BASE_URL = "BaseUrlServer"
    }

    internal val singleObserver = SingleSubject.create<String>()

    private val fireBaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    internal fun loadSettingApplicationFromServer() {
        fireBaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                fireBaseRemoteConfig.getString(REMOTE_CONFIG_KEY_BASE_URL).also { baseUrl ->
                    localRepository.saveConfigApplication(baseUrl)
                    singleObserver.onSuccess(baseUrl)
                }
            }
        }
    }
}
