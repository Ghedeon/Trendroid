package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.data.Api.TRENDING_URL
import com.ghedeon.trendroid.di.IO
import com.ghedeon.trendroid.domain.GithubRepository
import com.ghedeon.trendroid.domain.RepoDomain
import com.ghedeon.trendroid.domain.TrendingRepoDomain
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GithubDataRepository @Inject constructor(
	private val api: GithubApi,
	private val parser: Parser,
	@IO private val io: Scheduler
) : GithubRepository {
	
	override fun trending(): Single<List<TrendingRepoDomain>> = api.getTrending(TRENDING_URL)
		.map { parser.parse(it.string()) }
		.subscribeOn(io)
	
	override fun repo(url: String): Single<RepoDomain> = api.repo(url)
		.map { repoData ->
			repoData.toDomain()
		}
		.subscribeOn(io)
	
}
