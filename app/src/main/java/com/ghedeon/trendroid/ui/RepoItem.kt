package com.ghedeon.trendroid.ui

import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.KotlinModel
import com.ghedeon.trendroid.domain.Repo
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.repo_item.*

data class RepoItem(val data: Repo) : KotlinModel(R.layout.repo_item) {
	
	val clickObservable: Observable<String>
		get() = itemClickRelay
	
	private val itemClickRelay = PublishRelay.create<String>()
	private val disposables = CompositeDisposable()
	
	override fun bind() {
		RxView.clicks(requireView()).map { data.url }.subscribe(itemClickRelay).addTo(disposables)
		with(data) {
			val (repo, project) = name.split("/")
			repo_name.text = buildSpannedString {
				append("$repo/")
				bold { append(project) }
			}
			repo_description.text = description
			repo_stars.text = stars
			if (forks != null) {
				repo_forks.isVisible = true
				repo_forks.text = forks
			} else {
				repo_forks.isVisible = false
			}
			repo_stars_today.text = starsToday
		}
	}
	
	override fun unbind(view: View) {
		disposables.clear()
		super.unbind(view)
	}
}