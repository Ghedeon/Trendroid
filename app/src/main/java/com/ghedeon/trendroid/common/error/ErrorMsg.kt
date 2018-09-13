package com.ghedeon.trendroid.common.error

import android.content.Context
import androidx.annotation.StringRes
import com.ghedeon.trendroid.R

data class ErrorMsg(private val msg: String? = null, @StringRes private val msgRes: Int = R.string.generic_error) {
	fun resolve(context: Context?): String = msg ?: context?.getString(msgRes) ?: ""
}

