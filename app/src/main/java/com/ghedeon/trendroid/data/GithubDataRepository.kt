package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.di.IO
import com.ghedeon.trendroid.domain.GithubRepository
import com.ghedeon.trendroid.domain.Repo
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GithubDataRepository @Inject constructor(
	private val api: GithubApi,
	private val parser: Parser,
	@IO private val io: Scheduler
) : GithubRepository {
	
	override fun trending(): Single<List<Repo>> = api.getTrending()
		.map { parser.parse(it.string()) }
		.subscribeOn(io)
}
