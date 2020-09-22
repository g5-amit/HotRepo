package com.example.hotrepo.ui.trendingRepos

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.hotrepo.R
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import com.example.hotrepo.util.DataBindingIdlingResource
import com.example.hotrepo.util.EspressoIdlingResource
import com.example.hotrepo.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Responsible for Activity Click functionality Testing and Showing Listing of Repos
 * Fragments are not supported for hilt Activity Test , so test cases for fragment covered in Activity
 * */
class RepoActivityTest {

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
    fun check_SortByStars_menu_item_click() = runBlocking {

        // Start up Home screen
        val activityScenario = ActivityScenario.launch(RepoActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Open the options menu OR open the overflow menu, depending on whether
        // the device has a hardware or software overflow menu button.
        openActionBarOverflowOrOptionsMenu(
            ApplicationProvider.getApplicationContext<Context>()
        )
        onView(withText(R.string.sort_by_stars)).perform(click())

        // Make sure the activity is closed
        activityScenario.close()
    }

    @Test
    fun check_SortByName_menu_item_click() = runBlocking {

        // Start up Home screen
        val activityScenario = ActivityScenario.launch(RepoActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Open the options menu OR open the overflow menu, depending on whether
        // the device has a hardware or software overflow menu button.
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText(R.string.sort_by_name)).perform(click())

        // Make sure the activity is closed
        activityScenario.close()
    }

    @Test
    fun displayItemsInList() = runBlocking {

        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(RepoActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withId(R.id.recycler_view)).check(matches(not(emptyList<TrendingRepoEntity>())))
        onView(withText("TITLE1")).check(ViewAssertions.doesNotExist())

        // Make sure the activity is closed
        activityScenario.close()
    }

}