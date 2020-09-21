package com.example.hotrepo.data.remoteData

import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import com.example.hotrepo.data.network.utils.Resource

interface IRemoteTrendingRepo {
    suspend fun getTrendingRepoList(): Resource<List<TrendingRepoEntity>>
}