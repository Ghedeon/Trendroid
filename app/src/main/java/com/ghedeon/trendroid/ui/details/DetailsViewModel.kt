package com.ghedeon.trendroid.ui.details

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.ghedeon.trendroid.common.MobiusLogger
import com.ghedeon.trendroid.common.bind
import com.ghedeon.trendroid.common.error.ErrorHandler
import com.ghedeon.trendroid.domain.GithubRepository
import com.ghedeon.trendroid.ui.toDescriptionItem
import com.ghedeon.trendroid.ui.toInfoItem
import com.spotify.mobius.First.first
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.rx2.RxConnectables
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.rxkotlin.cast
import javax.inject.Inject


class DetailsViewModel @Inject constructor(
	private val githubRepos: GithubRepository
) : ViewModel() {
	
	private val loop: MobiusLoop.Factory<Model, Event, Effect> = RxMobius.loop(::update, ::effectHandler)
		.init { first(InitModel, setOf(LoadRepoEffect)) }
		.logger(MobiusLogger())
	
	private var controller: MobiusLoop.Controller<Model, Event> = Mobius.controller(loop, InitModel)
	
	lateinit var repoUrl: String
	
	fun bind(modelToEvent: (Observable<Model>) -> Observable<Event>, lifecycle: Lifecycle) {
		controller.connect(RxConnectables.fromTransformer(modelToEvent))
		controller.bind(lifecycle)
	}
	
	private fun update(model: Model, event: Event): Next<Model, Effect> = when (event) {
		is RepoLoadedEvent.Success -> {
			with(event.repo) {
				next<Model, Effect>(
					RepoDetailsModel(
						name,
						owner,
						avatarUrl,
						toDescriptionItem(),
						toInfoItem()
					)
				)
			}
		}
		is RepoLoadedEvent.Failure -> next(ErrorModel(event.msg))
	}
	
	private fun effectHandler(effects: Observable<Effect>): Observable<Event> = effects.flatMap { effect ->
		when (effect) {
			is LoadRepoEffect -> loadRepoDetails()
		}
	}
	
	private fun loadRepoDetails(): Observable<RepoLoadedEvent> = githubRepos.repo(repoUrl)
		.flatMapObservable { repoDetails ->
			Observable.just(RepoLoadedEvent.Success(repoDetails))
		}
		.cast<RepoLoadedEvent>()
		.onErrorReturn(ErrorHandler { msg ->
			RepoLoadedEvent.Failure(msg)
		})
}