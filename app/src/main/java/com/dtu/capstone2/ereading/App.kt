package com.dtu.capstone2.ereading

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository

/**
 * Create by Nguyen Van Phuc on 2/20/19
 */
class App : MultiDexApplication() {
    internal lateinit var localRepository: LocalRepository

    companion object {
        lateinit var instant: App
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instant = this
        localRepository = LocalRepository(applicationContext)
    }
}
