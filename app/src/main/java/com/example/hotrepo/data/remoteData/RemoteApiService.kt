package com.example.hotrepo.data.remoteData

import com.example.hotrepo.data.pojo.GitHubRepo
import com.example.hotrepo.utility.Constants
import retrofit2.Response
import retrofit2.http.GET

/**
 * All Api calls interfaces for Retrofit, will be defined here to deal with Network api
 */
interface RemoteApiService {
    @GET(Constants.REMOTE_TRENDING_REPO_URI)
    suspend fun getRemoteTrendingRepos(): Response<List<GitHubRepo>>
}