@file:Suppress("HasPlatformType")

package com.ghedeon.trendroid.di

import com.ghedeon.trendroid.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		AndroidSupportInjectionModule::class,
		UiModule::class,
		DataModule::class
	]
)
interface AppComponent : AndroidInjector<App> {
	
	@Component.Builder
	abstract class Builder : AndroidInjector.Builder<App>()
}
