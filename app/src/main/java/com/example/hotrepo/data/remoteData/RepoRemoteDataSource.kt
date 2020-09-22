package com.example.hotrepo.data.remoteData

import android.content.Context
import com.example.hotrepo.data.dataSource.RepoDataSource
import com.example.hotrepo.data.network.apiService.RemoteApiService
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import com.example.hotrepo.data.network.utils.BaseDataSource
import com.example.hotrepo.data.network.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Remote Source has responsibility to parse and deliver remote data to Room database
 * Once latest or refreshed data is saved in Room DB , it is observed on UI using Flow
 */
class RepoRemoteDataSource @Inject constructor(
    private val remoteApiService: RemoteApiService,
    @ApplicationContext appContext: Context
): BaseDataSource(appContext), RepoDataSource {

    override suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun getNetworkRepoList(): Resource<List<TrendingRepoEntity>> {

        val res = getResult { remoteApiService.getRemoteTrendingRepos() }
        when(res.status){
            Resource.Status.SUCCESS->{
                res.data?.let {
                    it.map { item -> TrendingRepoEntity.parseToRepoDBData(item) }
                }?.let { return Resource.success(it) }
            }
            Resource.Status.OFFLINE->{
                return Resource.offline()
            }
            else -> return Resource.error(res.message?:"")
        }
        return Resource.error(res.message?:"")
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

    override suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>> {
        TODO("Not yet implemented")
    }

}