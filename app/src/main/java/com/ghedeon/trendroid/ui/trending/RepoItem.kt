package com.ghedeon.trendroid.ui.trending

import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.KotlinModel
import com.ghedeon.trendroid.common.setTextOrHide
import kotlinx.android.extensions.CacheImplementation.NO_CACHE
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.repo_item.*

@ContainerOptions(cache = NO_CACHE)
data class RepoItem(
	val name: String,
	val url: String,
	val description: String,
	val stars: String,
	val forks: String?,
	val starsToday: String?
) : KotlinModel(R.layout.repo_item), LayoutContainer {
	
	private var onClickAction: (url: String) -> Unit = {}
	fun onClick(action: (url: String) -> Unit) = apply { onClickAction = action }
	
	override fun bind() {
		requireView().setOnClickListener { onClickAction(url) }
		
		val (repo, project) = name.split("/")
		repo_name.text = buildSpannedString {
			append("$repo/")
			bold { append(project) }
		}
		repo_owner_tv.text = description
		repo_stars.text = stars
		repo_forks.setTextOrHide(forks)
		repo_stars_today.setTextOrHide(starsToday)
	}
}