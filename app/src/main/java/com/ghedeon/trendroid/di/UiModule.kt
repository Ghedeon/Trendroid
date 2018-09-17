package com.ghedeon.trendroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghedeon.trendroid.common.ViewModelFactory
import com.ghedeon.trendroid.ui.MainActivity
import com.ghedeon.trendroid.ui.details.DetailsFragment
import com.ghedeon.trendroid.ui.details.DetailsViewModel
import com.ghedeon.trendroid.ui.trending.TrendingFragment
import com.ghedeon.trendroid.ui.trending.TrendingViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [FragmentModule::class, ViewModelModule::class])
abstract class UiModule {
	
	@ContributesAndroidInjector
	abstract fun mainActivity(): MainActivity
}

@Module
abstract class FragmentModule {
	
	@ContributesAndroidInjector
	abstract fun mainFragment(): TrendingFragment
	
	@ContributesAndroidInjector
	abstract fun detailsFragment(): DetailsFragment
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
	
	@Binds
	@IntoMap
	@ViewModelKey(DetailsViewModel::class)
	fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel
	
	
}
