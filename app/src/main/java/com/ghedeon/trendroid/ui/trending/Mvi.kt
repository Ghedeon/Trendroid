package com.ghedeon.trendroid.ui.trending

import com.ghedeon.trendroid.ui.base.ErrorMsg


sealed class Event
data class RepoClickedEvent(val url: String) : Event()

sealed class Model
object InitModel : Model()
data class DisplayReposModel(val repos: List<RepoItem>) : Model()
data class ErrorModel(val msg: ErrorMsg) : Model()


sealed class Effect
data class OpenRepoEffect(val url: String) : Effect()