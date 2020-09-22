package com.example.hotrepo.di.scopes

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RepoRemoteDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RepoLocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TrendingRepository