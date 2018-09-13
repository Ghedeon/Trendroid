package com.ghedeon.trendroid.ui.trending

import com.ghedeon.trendroid.common.error.ErrorMsg
import com.ghedeon.trendroid.domain.Repo


sealed class Event

object InitEvent : Event()
sealed class ReposLoadedEvent : Event() {
	data class Success(val repos: List<Repo>) : ReposLoadedEvent()
	data class Failure(val msg: ErrorMsg) : ReposLoadedEvent()
}

data class RepoClickedEvent(val url: String) : Event()

