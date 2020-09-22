package com.example.hotrepo.data.repository

import com.example.hotrepo.data.dataSource.RepoDataSource
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import com.example.hotrepo.data.localData.RepoLocalDataSource
import com.example.hotrepo.data.remoteData.RepoRemoteDataSource
import com.example.hotrepo.data.network.utils.Resource
import com.example.hotrepo.data.network.utils.performNetworkOperation
import kotlinx.coroutines.CoroutineDispatcher
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
open class TrendingRepository @Inject constructor(
    @com.example.hotrepo.di.scopes.RepoRemoteDataSource
    private val remoteDataSource: RepoDataSource,
    @com.example.hotrepo.di.scopes.RepoLocalDataSource
    private val localDataSource: RepoDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): BaseRepoRepository {

    override suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>> =
        localDataSource.getTrendingRepoList().flowOn(ioDispatcher)

    override suspend fun doNetworkJob() : Resource<List<TrendingRepoEntity>?> {
        return performNetworkOperation(
            networkCall = { remoteDataSource.getNetworkRepoList() },
            saveCallResult = { localDataSource.saveAndDeleteRepoList(it) }
        )
    }

    override suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrendingRepo(trendingRepoEntity: TrendingRepoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrendingRepoList() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrendingRepo(repoUrl: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveAndDeleteRepoList(trendingRepoEntityList: List<TrendingRepoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getNetworkRepoList(): Resource<List<TrendingRepoEntity>> {
        TODO("Not yet implemented")
    }


}