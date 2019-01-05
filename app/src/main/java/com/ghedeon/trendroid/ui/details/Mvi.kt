package com.ghedeon.trendroid.ui.details

import com.ghedeon.trendroid.ui.base.ErrorMsg

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