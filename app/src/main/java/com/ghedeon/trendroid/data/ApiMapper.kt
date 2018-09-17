package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.domain.RepoDomain


fun RepoData.toDomain() = RepoDomain(
	name = name,
	owner = owner.login,
	url = url,
	avatarUrl = owner.avatarUrl,
	description = description,
	stargasers = stargazers,
	watchers = watchers,
	forks = forks,
	issues = issues
)