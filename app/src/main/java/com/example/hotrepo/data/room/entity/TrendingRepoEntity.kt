package com.example.hotrepo.data.room.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hotrepo.data.network.response.BuiltBy
import com.example.hotrepo.data.network.response.GitHubRepo

/**
 * Table for the Trending repo List , We keep this table from Pojo so that if there is any change in api response signature,
 * then it will not effect DB table or it can be altered smoothly
 * */
@Entity(tableName = "TrendingRepoEntity")
data class TrendingRepoEntity(
    var author: String?,
    var name: String?,
    var avatar: String?,
    var description: String?,
    var stars: Int?,
    var forks: Int?,
    var currentPeriodStars: Int?,

    var language: String? ,
    var languageColor: String? ,

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "repoUrl")
    var url: String,

    var builtBy: List<BuiltBy>?,
    var timeStamp: Long = 0L
) {

    companion object {
        fun parseToRepoDBData(gitHubRepo: GitHubRepo): TrendingRepoEntity {
            gitHubRepo.let {
                return TrendingRepoEntity(
                    gitHubRepo.author,
                    gitHubRepo.name,
                    gitHubRepo.avatar,
                    gitHubRepo.description,
                    gitHubRepo.stars,
                    gitHubRepo.forks,
                    gitHubRepo.currentPeriodStars,
                    gitHubRepo.language,
                    gitHubRepo.languageColor,
                    gitHubRepo.url,
                    gitHubRepo.builtBy,
                    System.currentTimeMillis()
                )
            }
        }
    }
}

