package com.ghedeon.trendroid.ui.trending

import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.KotlinModel
import com.ghedeon.trendroid.common.setTextOrHide
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.repo_item.*

data class RepoItem(
	val name: String,
	val url: String,
	val description: String,
	val stars: String,
	val forks: String?,
	val starsToday: String?
) : KotlinModel(R.layout.repo_item) {
	
	val clickObservable: Observable<String>
		get() = itemClickRelay
	
	private val itemClickRelay = PublishRelay.create<String>()
	private val disposables = CompositeDisposable()
	
	override fun bind() {
		RxView.clicks(requireView()).map { url }.subscribe(itemClickRelay).addTo(disposables)
		val (repo, project) = name.split("/")
		repo_name.text = buildSpannedString {
			append("$repo/")
			bold { append(project) }
		}
		repo_owner_tv.text = description
		repo_stars.text = stars
		repo_forks.setTextOrHide(forks)
		repo_stars_today.setTextOrHide(starsToday)
	}
	
	override fun unbind(view: View) {
		disposables.clear()
		super.unbind(view)
	}
}