package com.example.hotrepo.ui.trendingRepos

import com.example.hotrepo.data.entity.TrendingRepoEntity
import com.example.hotrepo.data.pojo.BuiltBy

/**
 * Responsible for UI item view if in future UI changes we can change this class and DB Entity class will be intact
 * */
data class RepoItemUIModel(
    var author: String?,
    var name: String?,
    var avatar: String?,
    var description: String?,
    var stars: Int?,
    var forks: Int?,
    var currentPeriodStars: Int?,
    var language: String?,
    var languageColor: String?,
    var url: String,
    var builtBy: List<BuiltBy>?,
    var timeStamp: Long?
){
    var isExpanded : Boolean = false
    companion object {

        fun parseToUIData(trendingRepo: TrendingRepoEntity): RepoItemUIModel {
            trendingRepo.let {

                return RepoItemUIModel(
                    trendingRepo.author,
                    trendingRepo.name,
                    trendingRepo.avatar,
                    trendingRepo.description,
                    trendingRepo.stars,
                    trendingRepo.forks,
                    trendingRepo.currentPeriodStars,
                    trendingRepo.language,
                    trendingRepo.languageColor,
                    trendingRepo.url,
                    trendingRepo.builtBy,
                    System.currentTimeMillis()
                )
            }

        }
    }
}