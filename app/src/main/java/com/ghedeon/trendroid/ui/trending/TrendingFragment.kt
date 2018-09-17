package com.ghedeon.trendroid.ui.trending


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.*
import com.jakewharton.rxrelay2.PublishRelay
import com.yqritc.scalablevideoview.ScalableVideoView
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject

class TrendingFragment : DaggerFragment() {
	
	@Inject
	lateinit var viewModels: ViewModelProvider.Factory
	private val viewModel by bindViewModel<TrendingViewModel> { viewModels }
	private val events = PublishRelay.create<Event>()
	private var wasSplashShown = false
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
		container?.inflate(R.layout.fragment_trending)
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel.bind(::bindEvents, viewLifecycle)
	}
	
	private fun bindEvents(models: Observable<Model>): Observable<Event> {
		val disposable = models
			.observeOn(mainThread())
			.subscribe { model -> render(model) }
		
		return events.doOnDispose { disposable.dispose() }
	}
	
	private fun render(model: Model) {
		when (model) {
			is InitModel -> init()
			is DisplayReposModel -> {
				if (wasSplashShown) hideLoading()
				displayRepos(model.repos)
			}
			is OpenRepoModel -> openRepo(model.url)
			is ErrorModel -> context?.toast(model.msg.resolve(context))
		}
	}
	
	private fun init() {
		if (!wasSplashShown) {
			showLoadingVideo()
			actionBar.hide()
			wasSplashShown = true
		}
	}
	
	private fun displayRepos(repos: List<RepoItem>) {
		recycler_view.withModels {
			for (repo in repos) {
				repo.apply { clickObservable.map { RepoClickedEvent(url = it) }.subscribe(events) }
					.id(repo.url)
					.addTo(this)
			}
		}
	}
	
	private fun showLoadingVideo() {
		catchAll({ "playVideo" }) {
			(loading_video as ScalableVideoView).apply {
				isVisible = true
				setRawData(R.raw.loading)
				isLooping = true
				prepareAsync { start() }
			}
		}
	}
	
	private fun hideLoading() {
		if (!actionBar.isShowing) {
			actionBar.show()
			(loading_video as ScalableVideoView).stop()
			loading_video.isVisible = false
		}
	}
	
	private fun openRepo(url: String) {
		findNavController().navigate(TrendingFragmentDirections.openRepo(url))
	}
	
}