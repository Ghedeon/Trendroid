package com.ghedeon.trendroid.common

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified VIEW_MODEL : ViewModel> Fragment.bindViewModel(crossinline factory: () -> ViewModelProvider.Factory) =
	lazy {
		ViewModelProviders.of(this, factory.invoke())[VIEW_MODEL::class.java]
	}

inline val Fragment.actionBar: ActionBar
	get() = checkNotNull((activity as? AppCompatActivity)?.supportActionBar) { "ActionBar must not be null" }
