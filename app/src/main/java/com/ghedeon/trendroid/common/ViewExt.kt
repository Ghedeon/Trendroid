package com.ghedeon.trendroid.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
	LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun TextView.setTextOrHide(text: String?) {
	if (text != null) {
		isVisible = true
		this.text = text
	} else {
		isVisible = false
	}
}