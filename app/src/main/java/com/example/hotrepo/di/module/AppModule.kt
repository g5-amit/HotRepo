package com.example.hotrepo.di.module

import android.content.Context
import com.example.hotrepo.data.dataSource.RepoDataSource
import com.example.hotrepo.data.localData.RepoLocalDataSource
import com.example.hotrepo.data.network.apiService.RemoteApiService
import com.example.hotrepo.data.remoteData.RepoRemoteDataSource
import com.example.hotrepo.data.repository.TrendingRepository
import com.example.hotrepo.data.room.dao.TrendingRepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @com.example.hotrepo.di.scopes.RepoRemoteDataSource
    @Singleton
    @Provides
    fun provideRepoRemoteDataSource(remoteApiService: RemoteApiService, @ApplicationContext appContext: Context
    ):RepoDataSource
            = RepoRemoteDataSource(remoteApiService, appContext)


    @com.example.hotrepo.di.scopes.RepoLocalDataSource
    @Singleton
    @Provides
    fun provideRepoLocalDataSource(trendingRepoDao: TrendingRepoDao):RepoDataSource
            = RepoLocalDataSource(trendingRepoDao)

    @Singleton
    @Provides
    fun provideRepository(
        @com.example.hotrepo.di.scopes.RepoRemoteDataSource
        remoteDataSource: RepoDataSource,
        @com.example.hotrepo.di.scopes.RepoLocalDataSource
        localDataSource: RepoDataSource
    ) =
        TrendingRepository(remoteDataSource, localDataSource)
}