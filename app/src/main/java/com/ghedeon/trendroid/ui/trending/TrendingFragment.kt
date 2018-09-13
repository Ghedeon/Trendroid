package com.ghedeon.trendroid.ui.trending


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.*
import com.ghedeon.trendroid.domain.Repo
import com.ghedeon.trendroid.ui.RepoItem
import com.jakewharton.rxrelay2.PublishRelay
import com.yqritc.scalablevideoview.ScalableVideoView
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject

class TrendingFragment : DaggerFragment() {
	
	@Inject
	lateinit var viewModels: ViewModelProvider.Factory
	private val viewModel by bindViewModel<TrendingViewModel> { viewModels }
	private val events = PublishRelay.create<Event>()
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		container?.inflate(R.layout.fragment_trending)
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.bind(::bindEvents, lifecycle)
	}
	
	private fun bindEvents(models: Observable<Model>): Observable<Event> {
		val disposable = models
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { model -> render(model) }
		
		return events.doOnDispose(disposable::dispose)
	}
	
	private fun render(model: Model) {
		when (model) {
			is InitModel -> init()
			is DisplayReposModel -> displayRepos(model.repos)
			is OpenRepoModel -> openRepo(model.url)
			is ErrorModel -> context?.toast(model.msg.resolve(context))
		}
	}
	
	private fun init() {
		showLoadingVideo()
		actionBar.hide()
	}
	
	private fun showLoadingVideo() {
		catchAll({ "playVideo" }) {
			(loading_video as ScalableVideoView).apply {
				setRawData(R.raw.loading)
				isLooping = true
				prepareAsync { start() }
			}
		}
	}
	
	private fun hideLoading() {
		(loading_video as ScalableVideoView).stop()
		loading_video.isVisible = false
	}
	
	private fun displayRepos(repos: List<Repo>) {
		hideLoading()
		actionBar.show()
		recycler_view.withModels {
			for (repo in repos) {
				RepoItem(repo)
					.apply { clickObservable.map { RepoClickedEvent(url = it) }.subscribe(events) }
					.id(repo.url)
					.addTo(this)
			}
		}
	}
	
	private fun openRepo(url: String) {
		println(url)
	}
}