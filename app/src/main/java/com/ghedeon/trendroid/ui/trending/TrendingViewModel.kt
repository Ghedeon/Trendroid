package com.ghedeon.trendroid.ui.trending

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.ghedeon.trendroid.common.MobiusLogger
import com.ghedeon.trendroid.common.asMaybe
import com.ghedeon.trendroid.common.bind
import com.ghedeon.trendroid.common.error.ErrorHandler
import com.ghedeon.trendroid.domain.GithubRepository
import com.ghedeon.trendroid.domain.TrendingRepoDomain
import com.ghedeon.trendroid.ui.toRepoItem
import com.spotify.mobius.Effects.effects
import com.spotify.mobius.First.first
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.rx2.RxConnectables
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.rxkotlin.cast
import javax.inject.Inject


class TrendingViewModel @Inject constructor(
	private val githubRepos: GithubRepository
) : ViewModel() {
	
	private val loop: MobiusLoop.Factory<Model, Event, Effect> = RxMobius.loop(::update, ::effectHandler)
		.init { first(InitModel, setOf(InitEffect, LoadReposEffect)) }
		.logger(MobiusLogger())
	
	private var controller: MobiusLoop.Controller<Model, Event> = Mobius.controller(loop, InitModel)
	private var repos: List<TrendingRepoDomain>? = null
	
	fun bind(modelToEvent: (Observable<Model>) -> Observable<Event>, lifecycle: Lifecycle) {
		controller.connect(RxConnectables.fromTransformer(modelToEvent))
		controller.bind(lifecycle)
	}
	
	private fun update(model: Model, event: Event): Next<Model, Effect> = when (event) {
		is InitEvent -> if (repos == null) next<Model, Effect>(InitModel) else noChange<Model, Effect>()
		is LoadReposEvent -> dispatch(effects(LoadReposEffect))
		is ReposLoadedEvent.Success -> next(DisplayReposModel(event.repos.map { it.toRepoItem() }))
		is ReposLoadedEvent.Failure -> next(ErrorModel(event.msg))
		is RepoClickedEvent -> next(OpenRepoModel(event.url))
	}
	
	private fun effectHandler(effects: Observable<Effect>): Observable<Event> = effects.flatMap { effect ->
		when (effect) {
			is InitEffect -> Observable.just(InitEvent)
			is LoadReposEffect -> loadRepos()
		}
	}
	
	private fun loadRepos(): Observable<ReposLoadedEvent> =
		repos.asMaybe()
			.concatWith(githubRepos.trending().toMaybe())
			.firstOrError()
			.flatMapObservable { repos ->
				this.repos = repos
				Observable.just(ReposLoadedEvent.Success(repos))
			}
			.cast<ReposLoadedEvent>()
			.onErrorReturn(ErrorHandler { msg ->
				ReposLoadedEvent.Failure(msg)
			})
}