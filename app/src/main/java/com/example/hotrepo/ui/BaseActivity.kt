package com.example.hotrepo.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Common Activity for providing and reusing functionality through inheritance
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        initView(savedInstanceState)
    }

    /**
     * get Layout xml file for current activity
    */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * Responsible for All initial view initialization of an activity
     */
    abstract fun initView(savedInstanceState: Bundle?)
}