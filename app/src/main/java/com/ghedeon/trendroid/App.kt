package com.ghedeon.trendroid

import android.annotation.SuppressLint
import com.ghedeon.trendroid.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


@SuppressLint("Registered")
open class App : DaggerApplication() {
	
	override fun applicationInjector(): AndroidInjector<App> =
		DaggerAppComponent.builder().create(this)
}
