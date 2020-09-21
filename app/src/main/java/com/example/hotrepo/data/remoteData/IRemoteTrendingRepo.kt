package com.example.hotrepo.data.remoteData

import com.example.hotrepo.data.entities.TrendingRepoEntity
import com.example.hotrepo.data.networkWrapper.Resource

interface IRemoteTrendingRepo {
    suspend fun getTrendingRepoList(): Resource<List<TrendingRepoEntity>>
}