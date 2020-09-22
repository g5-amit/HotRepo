package com.example.hotrepo.data.repository

import com.example.hotrepo.data.network.utils.Resource
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow

/**
 * For ViewModel injection TrendingRepository and FakeRepository will be injected from fragment using DI
 * */
interface BaseRepoRepository {

    suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity?>

    suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>>

    suspend fun saveTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity>)

    suspend fun saveTrendingRepo(trendingRepoEntity: TrendingRepoEntity)

    suspend fun deleteTrendingRepoList()

    suspend fun deleteTrendingRepo(repoUrl: String)

    suspend fun saveAndDeleteRepoList(trendingRepoEntityList: List<TrendingRepoEntity>)

    suspend fun getNetworkRepoList(): Resource<List<TrendingRepoEntity>>

    suspend fun doNetworkJob() : Resource<List<TrendingRepoEntity>?>
}