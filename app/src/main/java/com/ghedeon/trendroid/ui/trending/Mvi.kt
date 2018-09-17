package com.ghedeon.trendroid.ui.trending

import com.ghedeon.trendroid.common.error.ErrorMsg
import com.ghedeon.trendroid.domain.TrendingRepoDomain


sealed class Event
object InitEvent : Event()
object LoadReposEvent : Event()
data class RepoClickedEvent(val url: String) : Event()
sealed class ReposLoadedEvent : Event() {
	data class Success(val repos: List<TrendingRepoDomain>) : ReposLoadedEvent()
	data class Failure(val msg: ErrorMsg) : ReposLoadedEvent()
}


sealed class Model
object InitModel : Model()
data class DisplayReposModel(val repos: List<RepoItem>) : Model()
data class OpenRepoModel(val url: String) : Model()
data class ErrorModel(val msg: ErrorMsg) : Model()


sealed class Effect
object LoadReposEffect : Effect()
object InitEffect : Effect()