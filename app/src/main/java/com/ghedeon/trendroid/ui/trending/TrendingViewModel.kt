package com.ghedeon.trendroid.ui.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ghedeon.trendroid.common.SingleLiveEvent
import com.ghedeon.trendroid.domain.GithubRepository
import com.ghedeon.trendroid.ui.base.BaseViewModel
import com.ghedeon.trendroid.ui.base.ErrorHandler
import com.ghedeon.trendroid.ui.toRepoItem
import kotlinx.coroutines.launch
import javax.inject.Inject


class TrendingViewModel @Inject constructor(private val githubRepos: GithubRepository) :
	BaseViewModel() {
	
	private val _uiModel = MutableLiveData<Model>().apply { value = InitModel }
	val uiModel: LiveData<Model> get() = _uiModel
	
	private val _uiEffects = SingleLiveEvent<Effect>()
	val uiEffects: LiveData<Effect> get() = _uiEffects
	
	private val errorHandler = ErrorHandler {
		_uiModel.value = ErrorModel(it)
	}
	
	fun onObserve() {
		loadRepos()
	}
	
	fun onUiEvent(event: Event) {
		println(event)
		when (event) {
			is RepoClickedEvent -> _uiEffects.value = OpenRepoEffect(event.url)
		}
	}
	
	private fun loadRepos() = launch(errorHandler) {
		val repos = githubRepos.trending().map { it.toRepoItem() }
		_uiModel.value = DisplayReposModel(repos)
	}
}