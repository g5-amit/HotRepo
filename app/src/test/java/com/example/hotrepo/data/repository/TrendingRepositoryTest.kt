package com.example.hotrepo.data.repository

import com.example.hotrepo.data.dataSource.FakeDataSource
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import com.example.hotrepo.util.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.hamcrest.core.IsEqual
import org.junit.*

/**
 * Responsible for testing the Repository , Data Source are mocked for Dummy Data
 * FakeDataSource will implement BaseDataSource to get all functionality of data Sources
 * */
class TrendingRepositoryTest {

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

    private val remoteRepos = listOf(tempRepo1, tempRepo2).sortedBy { it.stars }
    private val localRepos = listOf(tempRepo3).sortedBy { it.stars }
    private lateinit var repoRemoteDataSource: FakeDataSource
    private lateinit var repoLocalDataSource: FakeDataSource

    // Class under test
    private lateinit var trendingRepository: TrendingRepository

//     Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @Before
    fun createRepository() {
        repoRemoteDataSource = FakeDataSource(remoteRepos.toMutableList())
        repoLocalDataSource = FakeDataSource(localRepos.toMutableList())
        // Get a reference to the class under test
        trendingRepository = TrendingRepository(
            repoRemoteDataSource, repoLocalDataSource, Dispatchers.Main
        )
    }

    @Test
    fun getRepos_requestsAllReposFromLocalDataSource() = mainCoroutineRule.runBlockingTest {
        /**
         * repos are requested from the repository
         * It will fetch Data from Local DB as source of Truth for data consistency
         */
        val repos = trendingRepository.getTrendingRepoList()

        repos.collect {
            /**
             * Check repos from local data Source and assert with fake local repos data
             * */
            Assert.assertThat(it, IsEqual(localRepos))
        }
    }

    /**
     * repos are requested from the repository
     * It will fetch Data from Remote Source first and will save it to local DB
     * Al last it will fetch Data from Local DB as source of Truth for data consistency
     */
    @Test
    fun getRepos_requestsAllReposFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {

        trendingRepository.doNetworkJob()

        val repos = trendingRepository.getTrendingRepoList()

        repos.collect {
            /**
             * save repos from remote data Source t local source and assert with fake local repos data
             * */
            Assert.assertThat(it, IsEqual(localRepos))
        }
    }



}