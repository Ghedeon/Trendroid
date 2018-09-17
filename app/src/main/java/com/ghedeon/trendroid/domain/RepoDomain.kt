package com.ghedeon.trendroid.domain


data class RepoDomain(
	val name: String,
	val owner: String,
	val url: String,
	val avatarUrl: String,
	val description: String,
	val stargasers: Int,
	val watchers: Int,
	val forks: Int,
	val issues: Int
)