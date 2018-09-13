package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.data.Api.TRENDING_PATH
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET


interface GithubApi {
	
	@GET(TRENDING_PATH)
	fun getTrending(): Single<ResponseBody>
}
