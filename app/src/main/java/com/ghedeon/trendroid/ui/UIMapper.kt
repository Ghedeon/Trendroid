package com.ghedeon.trendroid.ui

import com.ghedeon.trendroid.domain.RepoDomain
import com.ghedeon.trendroid.domain.TrendingRepoDomain
import com.ghedeon.trendroid.ui.details.DescriptionItem
import com.ghedeon.trendroid.ui.details.InfoItem
import com.ghedeon.trendroid.ui.trending.RepoItem

fun TrendingRepoDomain.toRepoItem() = RepoItem(
	name = name,
	url = url,
	description = description,
	stars = stars,
	forks = forks,
	starsToday = starsToday
)

fun RepoDomain.toInfoItem() = InfoItem(
	issues = issues.toString(),
	stargazers = stargasers.toString(),
	forks = forks.toString(),
	watchers = watchers.toString()
)

fun RepoDomain.toDescriptionItem() = DescriptionItem(description = description, url = url)