package com.ghedeon.trendroid.common

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

inline val Fragment.actionBar: ActionBar
	get() = checkNotNull((activity as? AppCompatActivity)?.supportActionBar) { "ActionBar must not be null" }

inline val Fragment.viewLifecycle: Lifecycle
	get() = viewLifecycleOwner.lifecycle