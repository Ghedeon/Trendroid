package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.data.Api.REPO_PATH
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface GithubApi {
	
	@GET
	fun getTrending(@Url url: String): Deferred<ResponseBody>
	
	@GET(REPO_PATH)
	fun repo(@Path("repo_url", encoded = true) url: String): Deferred<RepoData>
}
