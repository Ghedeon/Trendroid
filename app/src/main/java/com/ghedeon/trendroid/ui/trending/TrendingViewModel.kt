package com.ghedeon.trendroid.ui.trending

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.ghedeon.trendroid.common.bind
import com.ghedeon.trendroid.common.error.ErrorHandler
import com.ghedeon.trendroid.di.ForEffect
import com.ghedeon.trendroid.di.ForEvent
import com.ghedeon.trendroid.domain.GithubRepository
import com.spotify.mobius.First
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Next
import com.spotify.mobius.runners.WorkRunner
import com.spotify.mobius.rx2.RxConnectables
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.rxkotlin.cast
import javax.inject.Inject


class TrendingViewModel @Inject constructor(
	private val githubRepos: GithubRepository,
	@ForEvent private val eventRunner: WorkRunner,
	@ForEffect private val effectRunner: WorkRunner
) : ViewModel() {
	
	private val loop: MobiusLoop.Factory<Model, Event, Effect> = RxMobius.loop(::update, ::effectHandler)
		.init { First.first(InitModel, setOf(InitEffect, LoadReposEffect)) }
		.eventRunner { eventRunner }
		.effectRunner { effectRunner }
	
	private var controller: MobiusLoop.Controller<Model, Event> = Mobius.controller(loop, InitModel)
	
	fun bind(modelToEvent: (Observable<Model>) -> Observable<Event>, lifecycle: Lifecycle) {
		controller.connect(RxConnectables.fromTransformer(modelToEvent))
		controller.bind(lifecycle)
	}
	
	private fun update(model: Model, event: Event): Next<Model, Effect> = when (event) {
		is InitEvent -> Next.next(InitModel)
		is ReposLoadedEvent.Success -> Next.next(DisplayReposModel(event.repos))
		is ReposLoadedEvent.Failure -> Next.next(ErrorModel(event.msg))
		is RepoClickedEvent -> Next.next(OpenRepoModel(event.url))
	}
	
	private fun effectHandler(effects: Observable<Effect>): Observable<Event> = effects.flatMap { effect ->
		when (effect) {
			is InitEffect -> Observable.just(InitEvent)
			is LoadReposEffect -> loadRepos()
		}
	}
	
	private fun loadRepos(): Observable<ReposLoadedEvent> = githubRepos.trending()
		.flatMapObservable { repos ->
			Observable.just(ReposLoadedEvent.Success(repos))
		}
		.cast<ReposLoadedEvent>()
		.onErrorReturn(ErrorHandler { msg ->
			ReposLoadedEvent.Failure(msg)
		})
}