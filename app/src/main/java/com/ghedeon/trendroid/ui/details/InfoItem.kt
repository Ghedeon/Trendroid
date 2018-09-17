package com.ghedeon.trendroid.ui.details

import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.KotlinModel
import kotlinx.android.synthetic.main.info_item.*


data class InfoItem(
	val issues: String,
	val stargazers: String,
	val forks: String,
	val watchers: String
) : KotlinModel(R.layout.info_item) {
	
	override fun bind() {
		issues_tv.text = issues
		stargasers_tv.text = stargazers
		forks_tv.text = forks
		watchers_tv.text = watchers
	}
	
}