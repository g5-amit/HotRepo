package com.example.hotrepo.ui.trendingRepos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hotrepo.data.repository.FakeRepository
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import com.example.hotrepo.util.TestCoroutineRule
import com.example.hotrepo.util.getOrAwaitValue
import com.example.hotrepo.utility.SortUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Unit test to test the TrendingRepoViewModel
 * */
class TrendingRepoViewModelTest {

    private val tempRepo1 = TrendingRepoEntity(url = "https://google.com", name = "Google",
        author = "google", stars = 12000, timeStamp = System.currentTimeMillis(),
        avatar = "abc.com", description = "repo1", forks = 1000,
        currentPeriodStars = 100 , language = "C++", languageColor = "Black",
        builtBy= null,
    )

    private val tempRepo2 = TrendingRepoEntity(url = "https://fb.com", name = "fb",
        author = "facebook", stars = 2000, timeStamp = System.currentTimeMillis(),
        avatar = "abc.com", description = "repo1", forks = 1000,
        currentPeriodStars = 100 , language = "C++", languageColor = "Black",
        builtBy= null,
    )

    private val tempRepo3 = TrendingRepoEntity(url = "https://ms.com", name = "fb",
        author = "microsoft", stars = 2000, timeStamp = System.currentTimeMillis(),
        avatar = "abc.com", description = "repo1", forks = 1000,
        currentPeriodStars = 100 , language = "C++", languageColor = "Black",
        builtBy= null,
    )

    // Subject under test
    private lateinit var trendingRepoViewModel: TrendingRepoViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var trendingRepository: FakeRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        // We initialise the tasks to 3, with one active and two completed
        trendingRepository = FakeRepository()
        trendingRepository.addRepos(tempRepo1, tempRepo2, tempRepo3)
        trendingRepoViewModel = TrendingRepoViewModel(trendingRepository)
    }

    @Test
    fun getRepoList_observe_when_open_ReposList(){

        val repos = trendingRepoViewModel.getRepoList()

        // Then the new Repo List data is triggered via LiveData
        val value = trendingRepoViewModel.repoList.getOrAwaitValue()

        //Assert Data is not null
        MatcherAssert.assertThat(
            value, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun getRepoList_observe_when_SetNameFilter(){

        trendingRepoViewModel.setSortOrder(SortUtils.Sort.NAME)

        // Then the new Repo List data is triggered via LiveData
        val value = trendingRepoViewModel.repoList.getOrAwaitValue()

        //Assert Data is not null
        MatcherAssert.assertThat(
            value, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}