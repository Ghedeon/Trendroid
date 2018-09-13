package com.ghedeon.trendroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghedeon.trendroid.common.ViewModelFactory
import com.ghedeon.trendroid.ui.MainActivity
import com.ghedeon.trendroid.ui.trending.TrendingFragment
import com.ghedeon.trendroid.ui.trending.TrendingViewModel
import com.spotify.mobius.runners.WorkRunner
import com.spotify.mobius.rx2.SchedulerWorkRunner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module(includes = [FragmentModule::class, ViewModelModule::class])
abstract class UiModule {
	
	@ContributesAndroidInjector
	abstract fun mainActivity(): MainActivity
	
	@Module
	companion object {
		
		@ForEvent
		@Provides
		@JvmStatic
		fun eventRunner(): WorkRunner = SchedulerWorkRunner(Schedulers.computation())
		
		@ForEffect
		@Provides
		@JvmStatic
		fun effectRunner(): WorkRunner = SchedulerWorkRunner(Schedulers.computation())
	}
}

@Module
abstract class FragmentModule {
	
	@ContributesAndroidInjector
	abstract fun mainFragment(): TrendingFragment
}

@Module
interface ViewModelModule {
	
	@Binds
	@Singleton
	fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
	
	@Binds
	@IntoMap
	@ViewModelKey(TrendingViewModel::class)
	fun bindTrendingViewModel(viewModel: TrendingViewModel): ViewModel
	
	
}
