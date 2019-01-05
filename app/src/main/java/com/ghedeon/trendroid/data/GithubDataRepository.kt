package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.data.Api.TRENDING_URL
import com.ghedeon.trendroid.domain.GithubRepository
import com.ghedeon.trendroid.domain.RepoDomain
import com.ghedeon.trendroid.domain.TrendingRepoDomain
import javax.inject.Inject

class GithubDataRepository @Inject constructor(
	private val api: GithubApi,
	private val parser: Parser
) : GithubRepository {
	
	override suspend fun trending(): List<TrendingRepoDomain> =
		api.getTrending(TRENDING_URL)
			.await()
			.run { parser.parse(string()) }
	
	override suspend fun repo(url: String): RepoDomain = api.repo(url).await().toDomain()
}
