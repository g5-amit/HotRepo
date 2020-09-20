package com.example.hotrepo.data.remote

import android.content.Context
import com.example.hotrepo.data.entity.TrendingRepoEntity
import com.example.hotrepo.data.remote.networkWrapper.BaseDataSource
import com.example.hotrepo.data.remote.networkWrapper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Remote Source has responsibility to parse and deliver remote data to Room database
 * Once latest or refreshed data is saved in Room DB , it is observed on UI using Flow
 */
class RepoRemoteDataSource @Inject constructor(
    private val remoteApiService: RemoteApiService,
    @ApplicationContext appContext: Context
): BaseDataSource(appContext),IRemoteTrendingRepo {

    override suspend fun getTrendingRepoList(): Resource<List<TrendingRepoEntity>> {

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
}