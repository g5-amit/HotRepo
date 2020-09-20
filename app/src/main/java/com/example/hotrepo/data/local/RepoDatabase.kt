package com.example.hotrepo.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hotrepo.data.entity.TrendingRepoEntity
import com.example.hotrepo.utility.Constants

/**
 * Singleton Repo Database is defined to save Network Data for offline Support
 */

@Database(entities = [TrendingRepoEntity::class], version = 1, exportSchema = false)
@TypeConverters(RepoConverter::class)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun trendingRepoDao(): TrendingRepoDao
    companion object {
        @Volatile private var instance: RepoDatabase? = null

        fun getDatabase(context: Context): RepoDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, RepoDatabase::class.java, Constants.TRENDING_REPO_DB)
                .fallbackToDestructiveMigration()
                .build()
    }

}