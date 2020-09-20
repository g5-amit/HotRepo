package com.example.hotrepo.navigator

/**
 * Available screens.
 */
enum class Screens {
    TRENDING_REPO
}

/**
 * Interfaces that defines an app navigator.
 */
interface AppNavigator {

    /**
    * Navigate to a given screen.
    */
    fun navigateTo(screen: Screens)
}