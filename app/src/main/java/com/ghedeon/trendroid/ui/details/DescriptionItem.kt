package com.ghedeon.trendroid.ui.details

import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.KotlinModel
import kotlinx.android.synthetic.main.description_item.*


data class DescriptionItem(val description: String, val url: String) : KotlinModel(R.layout.description_item) {
	override fun bind() {
		description_tv.text = description
		url_tv.text = url
	}
}