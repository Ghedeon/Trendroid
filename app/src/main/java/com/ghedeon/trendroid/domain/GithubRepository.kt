package com.ghedeon.trendroid.domain


interface GithubRepository {
	
	suspend fun trending(): List<TrendingRepoDomain>
	
	suspend fun repo(url: String): RepoDomain
}
