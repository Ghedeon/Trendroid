package com.ghedeon.trendroid.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
	ViewModelProvider.Factory {
	
	override fun <VIEW_MODEL : ViewModel> create(modelClass: Class<VIEW_MODEL>): VIEW_MODEL =
		viewModels[modelClass]?.get() as VIEW_MODEL
}
