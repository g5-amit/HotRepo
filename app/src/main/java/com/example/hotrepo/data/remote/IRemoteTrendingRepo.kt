package com.example.hotrepo.data.remote

import com.example.hotrepo.data.entity.TrendingRepoEntity
import com.example.hotrepo.data.networkWrapper.Resource

interface IRemoteTrendingRepo {
    suspend fun getTrendingRepoList(): Resource<List<TrendingRepoEntity>>
}