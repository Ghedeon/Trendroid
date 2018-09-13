package com.ghedeon.trendroid.common

import android.view.View
import androidx.annotation.LayoutRes
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.extensions.LayoutContainer

abstract class KotlinModel(
	@LayoutRes private val layoutRes: Int
) : EpoxyModel<View>(), LayoutContainer {
	
	override val containerView: View?
		get() = view
	
	private var view: View? = null
	
	abstract fun bind()
	
	override fun bind(view: View) {
		this.view = view
		bind()
	}
	
	override fun unbind(view: View) {
		this.view = null
	}
	
	override fun getDefaultLayout() = layoutRes
	
	protected fun requireView() = requireNotNull(containerView) { "view must be not null" }
	
}