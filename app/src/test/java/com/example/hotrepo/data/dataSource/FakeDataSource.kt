package com.example.hotrepo.data.dataSource

import com.example.hotrepo.data.network.utils.Resource
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * Responsible to serve functionality for RepoLocalDataSource and RepoRemoteDataSource
 * Used for testing with fake data without corrupting DB data for testing
 * */
class FakeDataSource(var repoList: MutableList<TrendingRepoEntity>? = mutableListOf()) : RepoDataSource {

    override suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>> {
        repoList?.let { return flow{ArrayList(it)} }
        return flowOf(emptyList())
    }

    override suspend fun getNetworkRepoList(): Resource<List<TrendingRepoEntity>> {
        repoList?.let { return Resource.success(it.toList()) }
        return Resource.error("EmptyList")
    }

    override suspend fun saveTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity>) {
        repoList?.addAll(trendingRepoEntityList)
    }

    override suspend fun saveTrendingRepo(trendingRepoEntity: TrendingRepoEntity) {
        repoList?.add(trendingRepoEntity)
    }

    override suspend fun deleteTrendingRepoList() {
        repoList?.clear()
    }

    override suspend fun deleteTrendingRepo(repoUrl: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAndDeleteRepoList(trendingRepoEntityList: List<TrendingRepoEntity>) {
        repoList?.clear()
        repoList?.addAll(trendingRepoEntityList)
    }


}