package com.ghedeon.trendroid.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.inflate
import com.ghedeon.trendroid.common.toast
import com.ghedeon.trendroid.common.withModels
import com.ghedeon.trendroid.ui.details.DetailsFragmentArgs.fromBundle
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject


class DetailsFragment : DaggerFragment() {
	
	@Inject
	lateinit var viewModels: ViewModelProvider.Factory
	private val viewModel by viewModels<DetailsViewModel> { viewModels }
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
		container?.inflate(R.layout.fragment_details)
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel.repoUrl = fromBundle(arguments).repoUrl
		viewModel.uiModel.nonNull().observe(this, ::render)
	}
	
	private fun render(model: Model) {
		when (model) {
			is RepoDetailsModel -> {
				setHeroView(model)
				setDetails(model)
			}
			is ErrorModel -> context?.toast(model.msg.resolve(context))
		}
	}
	
	private fun setHeroView(model: RepoDetailsModel) {
		Glide.with(this).load(model.avatarUrl).into(backdrop)
		repo_name_tv.text = model.name
		repo_owner_tv.text = model.owner
	}
	
	private fun setDetails(model: RepoDetailsModel) {
		details_recycler.withModels {
			model.description.id("description").addTo(this)
			model.info.id("info").addTo(this)
		}
	}
}