package com.example.hotrepo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application used for showing the Trending Repo
 * All dependency will be injected through Hilt
 */
@HiltAndroidApp
class TrendingRepoApplication : Application() {

}