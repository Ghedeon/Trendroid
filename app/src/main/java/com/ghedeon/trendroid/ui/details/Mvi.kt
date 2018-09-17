package com.ghedeon.trendroid.ui.details

import com.ghedeon.trendroid.common.error.ErrorMsg
import com.ghedeon.trendroid.domain.RepoDomain

sealed class Model
object InitModel : Model()
data class ErrorModel(val msg: ErrorMsg) : Model()
data class RepoDetailsModel(
	val name: String,
	val owner: String,
	val avatarUrl: String,
	val description: DescriptionItem,
	val info: InfoItem
) : Model()


sealed class Event
sealed class RepoLoadedEvent : Event() {
	data class Success(val repo: RepoDomain) : RepoLoadedEvent()
	data class Failure(val msg: ErrorMsg) : RepoLoadedEvent()
}


sealed class Effect
object LoadRepoEffect : Effect()