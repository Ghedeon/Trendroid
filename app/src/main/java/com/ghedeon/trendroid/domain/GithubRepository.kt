package com.ghedeon.trendroid.domain

import io.reactivex.Single


interface GithubRepository {
	
	fun trending(): Single<List<TrendingRepoDomain>>
	
	fun repo(url: String): Single<RepoDomain>
}
