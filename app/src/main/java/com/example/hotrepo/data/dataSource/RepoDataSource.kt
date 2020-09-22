package com.example.hotrepo.data.dataSource

import com.example.hotrepo.data.network.utils.Resource
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction to query data from Room Database
 */
interface RepoDataSource {

    suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity?>

    suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>>

    suspend fun saveTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity>)

    suspend fun saveTrendingRepo(trendingRepoEntity: TrendingRepoEntity)

    suspend fun deleteTrendingRepoList()

    suspend fun deleteTrendingRepo(repoUrl: String)

    suspend fun saveAndDeleteRepoList(trendingRepoEntityList: List<TrendingRepoEntity>)

    suspend fun getNetworkRepoList(): Resource<List<TrendingRepoEntity>>

}