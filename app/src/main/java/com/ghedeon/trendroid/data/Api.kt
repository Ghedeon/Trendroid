package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.BuildConfig


object Api {
	
	const val API_URL = BuildConfig.GITHUB_API_URL
	const val REPO_PATH = "/repos{repo_url}"
	const val TRENDING_URL = "${BuildConfig.GITHUB_URL}/trending/kotlin"
}
