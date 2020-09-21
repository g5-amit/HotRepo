package com.example.hotrepo.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
* All Fragments in the app will inherit BaseFragment to reuse functionality
*/
abstract class BaseFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}