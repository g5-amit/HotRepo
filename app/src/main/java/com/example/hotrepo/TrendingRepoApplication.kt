package com.example.hotrepo

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

/**
 * Application used for showing the Trending Repo
 * All dependency will be injected through Hilt
 */
@HiltAndroidApp
class TrendingRepoApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}