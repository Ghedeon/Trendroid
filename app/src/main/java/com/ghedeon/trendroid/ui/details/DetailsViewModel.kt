package com.ghedeon.trendroid.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ghedeon.trendroid.domain.GithubRepository
import com.ghedeon.trendroid.ui.base.BaseViewModel
import com.ghedeon.trendroid.ui.base.ErrorHandler
import com.ghedeon.trendroid.ui.toDescriptionItem
import com.ghedeon.trendroid.ui.toInfoItem
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailsViewModel @Inject constructor(private val githubRepos: GithubRepository) : BaseViewModel() {
	
	lateinit var repoUrl: String
	
	private val _uiModel = MutableLiveData<Model>()
	val uiModel: LiveData<Model> get() = _uiModel
	
	private val errorHandler = ErrorHandler {
		_uiModel.value = ErrorModel(it)
	}
	
	init {
		_uiModel.value = InitModel
		loadRepoDetails()
	}
	
	private fun loadRepoDetails() {
		launch(errorHandler) {
			val model = githubRepos.repo(repoUrl).run {
				RepoDetailsModel(
					name,
					owner,
					avatarUrl,
					toDescriptionItem(),
					toInfoItem()
				)
			}
			_uiModel.value = model
		}
	}
}