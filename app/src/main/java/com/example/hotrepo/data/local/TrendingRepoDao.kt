package com.example.hotrepo.data.local

import androidx.room.*
import com.example.hotrepo.data.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * All SQL queries and operations will be written in Dao
 * Dao is reactive using Flow Stream and will be observed by UI if any change in DB for Flow Stream Queries
 */

@Dao
interface TrendingRepoDao {

    /**
     * Flow Queries are responsive and if there is any chane in database , it will update UI
     */
    @Query("SELECT * FROM TrendingRepoEntity ")
    fun getAllTrendingRepo() : Flow<List<TrendingRepoEntity>>

    @Query("SELECT * FROM TrendingRepoEntity WHERE repoUrl = :url")
    fun getTrendingRepoById(url: String): Flow<TrendingRepoEntity>

    /**
     * UI is only notified when the actual query results change by applying the distinctUntilChanged()
     */
    fun getRepoDistinctUntilChanged(url: String) =
        getTrendingRepoById(url).distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingRepoEntity(trendingRepoEntity: TrendingRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRepo(trendingRepoEntities: List<TrendingRepoEntity?>?)

    /**
     * @Transaction for bulk operation to commit operation in one DB lock
     * It helps to optimise expensive db open/close operation for every transaction
     */
    @Transaction
    suspend fun deleteAndSaveRepoList(trendingRepoEntities: List<TrendingRepoEntity>){
        deleteAllTrendingRepo()
        trendingRepoEntities?.let { insertAllRepo(trendingRepoEntities) }
    }

    @Query("DELETE FROM TrendingRepoEntity WHERE repoUrl = :url")
    suspend fun deleteTrendingRepoById(url: String?): Int

    /**
     * Delete all Users (items).
     */
    @Query("DELETE FROM TrendingRepoEntity")
    suspend fun deleteAllTrendingRepo()

    @Update
    suspend fun updateTrendingRepo(trendingRepoEntity: TrendingRepoEntity?): Int
}