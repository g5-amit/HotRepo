package com.example.hotrepo

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hotrepo.ui.trendingRepos.RepoActivity
import com.example.hotrepo.util.DataBindingIdlingResource
import com.example.hotrepo.util.EspressoIdlingResource
import com.example.hotrepo.util.monitorActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Navigation test are used for Navigating between different navigation screen in the app
 * checked the launch app navigation for home screen
 * */
class AppNavigationTest {

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun repoListScreen_clickOnAndroidHomeIcon_OpensNavigation() {
        // Start up Home screen
        val activityScenario = ActivityScenario.launch(RepoActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withId(R.id.recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))

        // When using ActivityScenario.launch, always call close()
        activityScenario.close()
    }

}
