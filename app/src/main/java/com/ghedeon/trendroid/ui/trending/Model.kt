package com.ghedeon.trendroid.ui.trending

import com.ghedeon.trendroid.common.error.ErrorMsg
import com.ghedeon.trendroid.domain.Repo

sealed class Model

object InitModel : Model()
data class DisplayReposModel(val repos: List<Repo>) : Model()
data class OpenRepoModel(val url: String) : Model()
data class ErrorModel(val msg: ErrorMsg) : Model()
