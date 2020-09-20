package com.example.hotrepo.data.local

import com.example.hotrepo.data.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Local Source is responsible to save Network data for offline support
 * All UI data is provided by Local Source from Room DB
 * It helps for the source of Truth of data consistency on UI
 */
class RepoLocalDataSource @Inject constructor(private val repoDao: TrendingRepoDao) : ILocalTrendingRepo {

    override suspend fun getTrendingRepo(repoUrl: String): Flow<TrendingRepoEntity> {
        return repoDao.getRepoDistinctUntilChanged(repoUrl)
    }

    override suspend fun getTrendingRepoList(): Flow<List<TrendingRepoEntity>> {
        return repoDao.getAllTrendingRepo()
    }

    override suspend fun saveTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity?>?) {
        repoDao.insertAllRepo(trendingRepoEntityList)
    }

    override suspend fun saveTrendingRepo(trendingRepoEntity: TrendingRepoEntity) {
        repoDao.insertTrendingRepoEntity(trendingRepoEntity)
    }

    override suspend fun deleteTrendingRepoList() {
        repoDao.deleteAllTrendingRepo()
    }

    override suspend fun deleteTrendingRepo(repoUrl: String) {
        repoDao.deleteTrendingRepoById(repoUrl)
    }

    override suspend fun saveAndDeleteRepoList(trendingRepoEntityList: List<TrendingRepoEntity>) {
        repoDao.deleteAndSaveRepoList(trendingRepoEntityList)
    }

}