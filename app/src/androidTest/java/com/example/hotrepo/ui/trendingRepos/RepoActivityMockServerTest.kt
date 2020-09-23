package com.example.hotrepo.ui.trendingRepos

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.hotrepo.R
import com.example.hotrepo.di.module.NetworkModule
import com.example.hotrepo.util.FileReader
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Used to test MockServer Response testing
* */
@UninstallModules(NetworkModule::class)
@HiltAndroidTest
class RepoActivityMockServerTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(RepoActivity::class.java, true, false)

    private  var mockServer = MockWebServer()

    @Before
    fun setUp() {
        mockServer.start(8080)
    }

    @After
    fun tearDown() =
        mockServer.shutdown()

    @Test
    fun happySuccessTestCase() {
        mockServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readStringFromFile("success_response.json"))
            }
        }
        activityRule.launchActivity(null)
        Espresso.onView(withId(R.id.recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("TITLE1")).check(ViewAssertions.doesNotExist())
    }

    /**
     * To check this test case Please clear App data from app settings
     * Otherwise DB list will be inflated and Api response does not matter
     * */
    @Test
    fun unHappyFailureTestCase() {
        mockServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }
        activityRule.launchActivity(null)
        Espresso.onView(withId(R.id.error_retry)).run {
            check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        }
    }
}