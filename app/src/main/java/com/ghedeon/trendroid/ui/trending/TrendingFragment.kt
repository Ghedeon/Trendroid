package com.ghedeon.trendroid.ui.trending


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.actionBar
import com.ghedeon.trendroid.common.catchAll
import com.ghedeon.trendroid.common.inflate
import com.ghedeon.trendroid.common.toast
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import com.yqritc.scalablevideoview.ScalableVideoView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject

class TrendingFragment : DaggerFragment() {
	
	@Inject
	lateinit var viewModels: ViewModelProvider.Factory
	private val viewModel by viewModels<TrendingViewModel> { viewModels }
	private var wasSplashShown = false
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
		container?.inflate(R.layout.fragment_trending)
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel.onObserve()
		viewModel.uiModel.nonNull().observe(this, ::render)
		viewModel.uiEffects.observe(this, ::onEffect)
	}
	
	private fun render(model: Model) {
		when (model) {
			is InitModel -> init()
			is DisplayReposModel -> {
				if (wasSplashShown) hideLoading()
				displayRepos(model.repos)
			}
			is ErrorModel -> context?.toast(model.msg.resolve(context))
		}
	}
	
	private fun onEffect(effect: Effect?) {
		println(effect)
		when (effect) {
			is OpenRepoEffect -> openRepo(effect.url)
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
		for (repo in repos) {
			repo.onClick { viewModel.onUiEvent(RepoClickedEvent(url = it)) }
				.id(repo.url)
			println(repo)
		}
		recycler_view.setModels(repos)
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