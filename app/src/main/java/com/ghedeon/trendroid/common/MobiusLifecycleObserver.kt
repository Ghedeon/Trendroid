package com.ghedeon.trendroid.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.spotify.mobius.MobiusLoop


class MobiusLifecycleObserver<MODEL, EVENT>(
	val lifecycle: Lifecycle,
	private val controller: MobiusLoop.Controller<MODEL, EVENT>
) : DefaultLifecycleObserver {
	
	init {
		lifecycle.addObserver(this)
	}
	
	override fun onStart(owner: LifecycleOwner) = controller.start()
	
	override fun onStop(owner: LifecycleOwner) = controller.stop()
	
	override fun onDestroy(owner: LifecycleOwner) {
		controller.disconnect()
		lifecycle.removeObserver(this)
	}
}

fun <MODEL, EVENT> MobiusLoop.Controller<MODEL, EVENT>.bind(lifecycle: Lifecycle) {
	MobiusLifecycleObserver(lifecycle, this)
}

