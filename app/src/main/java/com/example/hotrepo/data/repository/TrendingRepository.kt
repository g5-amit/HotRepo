package com.example.hotrepo.data.repository

import com.example.hotrepo.data.entities.TrendingRepoEntity
import com.example.hotrepo.data.localData.RepoLocalDataSource
import com.example.hotrepo.data.remoteData.RepoRemoteDataSource
import com.example.hotrepo.data.networkWrapper.Resource
import com.example.hotrepo.data.networkWrapper.performNetworkOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Responsible for fetching data from Room DB and update UI using reactive stream of Flow
 * Responsible for fetching remote Data from Network and save it to DB
 * */
@Singleton
class TrendingRepository @Inject constructor(
    private val remoteDataSource: RepoRemoteDataSource,
    private val localDataSource: RepoLocalDataSource
) {

    suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>> =
        localDataSource.getTrendingRepoList().flowOn(Dispatchers.IO)

    suspend fun doNetworkJob() : Resource<List<TrendingRepoEntity>?> {
        return performNetworkOperation(
            networkCall = { remoteDataSource.getTrendingRepoList() },
            saveCallResult = { localDataSource.saveAndDeleteRepoList(it) }
        )
    }
}