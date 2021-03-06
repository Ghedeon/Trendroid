package com.ghedeon.trendroid.data

import com.squareup.moshi.Json

data class RepoData(
	@field:Json(name = "name") val name: String,
	@field:Json(name = "html_url") val url: String,
	@field:Json(name = "owner") val owner: Owner,
	@field:Json(name = "description") val description: String,
	@field:Json(name = "stargazers_count") val stargazers: Int,
	@field:Json(name = "watchers_count") val watchers: Int,
	@field:Json(name = "forks_count") val forks: Int,
	@field:Json(name = "open_issues_count") val issues: Int
)

data class Owner(
	@field:Json(name = "login") val login: String,
	@field:Json(name = "avatar_url") val avatarUrl: String
)
