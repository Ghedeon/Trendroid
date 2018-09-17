package com.ghedeon.trendroid.domain


data class TrendingRepoDomain(
	val name: String,
	val url: String,
	val description: String,
	val stars: String,
	val forks: String?,
	val starsToday: String?
)