package com.example.hotrepo

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * This Runner is required to configure in build.gradle so that TestApplication context can be used
 * Used for MockServer testing for Retrofit api response
 * */
class MockTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?,
                                context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

}