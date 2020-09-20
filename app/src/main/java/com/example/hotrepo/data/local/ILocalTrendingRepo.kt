package com.example.hotrepo.data.local

import com.example.hotrepo.data.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction to query data from Room Database
 */
interface ILocalTrendingRepo {

    suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity>

    suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>>

    suspend fun saveTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity?>?)

    suspend fun saveTrendingRepo(trendingRepoEntity: TrendingRepoEntity)

    suspend fun deleteTrendingRepoList()

    suspend fun deleteTrendingRepo(repoUrl: String)

    suspend fun saveAndDeleteRepoList(trendingRepoEntityList: List<TrendingRepoEntity>)

}