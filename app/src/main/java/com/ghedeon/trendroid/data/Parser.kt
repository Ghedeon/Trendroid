package com.ghedeon.trendroid.data

import com.ghedeon.trendroid.domain.Repo
import org.jsoup.Jsoup
import javax.inject.Inject


class Parser @Inject constructor() {
	
	fun parse(html: String): List<Repo> = Jsoup.parse(html)
		.select("ol.repo-list").first().select("li")
		.map { repoHtml ->
			with(repoHtml) {
				val (name, url) = select("h3 > a").run { text() to attr("href") }
				val description = select("div.py-1").text()
				val (stars, forks) = select("a.muted-link").run { first().text() to secondOrNull()?.text() }
				val starsToday = select("span.float-sm-right").first().text()
				Repo(name, url, description, stars, forks, starsToday)
			}
		}.filter { it.description.contains("android", ignoreCase = true) }
}

fun <E> List<E>.secondOrNull(): E? = if (this.size >= 2) this[1] else null