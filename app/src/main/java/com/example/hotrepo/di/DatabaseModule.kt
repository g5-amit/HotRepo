package com.example.hotrepo.di

import android.content.Context
import com.example.hotrepo.data.localData.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = RepoDatabase.getDatabase(
        appContext
    )

    @Singleton
    @Provides
    fun provideRepoDao(db: RepoDatabase) = db.trendingRepoDao()

}