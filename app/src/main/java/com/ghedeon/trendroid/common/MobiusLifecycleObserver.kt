package com.ghedeon.trendroid.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.spotify.mobius.MobiusLoop


class MobiusLifecycleObserver<MODEL, EVENT>(
	lifecycle: Lifecycle,
	private val controller: MobiusLoop.Controller<MODEL, EVENT>
) : LifecycleObserver {
	
	init {
		lifecycle.addObserver(this)
	}
	
	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	fun start() = controller.start()
	
	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	fun stop() = controller.stop()
	
	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	fun disconnect() = controller.disconnect()
}

fun <MODEL, EVENT> MobiusLoop.Controller<MODEL, EVENT>.bind(lifecycle: Lifecycle) {
	MobiusLifecycleObserver(lifecycle, this)
}

