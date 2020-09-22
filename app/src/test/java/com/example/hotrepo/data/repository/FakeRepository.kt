package com.example.hotrepo.data.repository

import com.example.hotrepo.data.network.utils.Resource
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Responsible for testing ViewModel , FakeRepository should not use Local and Remote Dta Source for ViewModel testing
 * All Dummy data is in memory of LinkedHashMap to avoid corruption of DB data
 * */
class FakeRepository : BaseRepoRepository{

    private var tasksServiceData: LinkedHashMap<String, TrendingRepoEntity> = LinkedHashMap()

    fun addRepos(vararg repos: TrendingRepoEntity) {
        for (repo in repos) {
            tasksServiceData[repo.url] = repo
        }
    }

    override suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity?> {
        return flowOf(tasksServiceData[repoUrl])

    }

    override suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>> {
        return flowOf(tasksServiceData.values.toList())
    }

    override suspend fun saveTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity>) {
        for (repo in trendingRepoEntityList) {
            tasksServiceData[repo.url] = repo
        }
    }

    override suspend fun saveTrendingRepo(trendingRepoEntity: TrendingRepoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrendingRepoList() {
        tasksServiceData.clear()
    }

    override suspend fun deleteTrendingRepo(repoUrl: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveAndDeleteRepoList(trendingRepoEntityList: List<TrendingRepoEntity>) {
        deleteTrendingRepoList()
        saveTrendingRepoList(trendingRepoEntityList)
    }

    override suspend fun getNetworkRepoList(): Resource<List<TrendingRepoEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun doNetworkJob(): Resource<List<TrendingRepoEntity>?> {
        saveAndDeleteRepoList(tasksServiceData.values.toList())
        return Resource.success(tasksServiceData.values.toList())
    }
}