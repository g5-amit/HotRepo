package com.example.hotrepo.navigator

import androidx.fragment.app.FragmentActivity
import com.example.hotrepo.R
import com.example.hotrepo.ui.trendingRepos.RepoFragment
import javax.inject.Inject

/**
 * Navigator implementation is responsible for the replacing fragments on Main Activity
 * All frag
 */
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.TRENDING_REPO -> RepoFragment()
        }
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .commit()
    }
}