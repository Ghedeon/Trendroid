package com.ghedeon.trendroid.ui.base

import android.content.Context
import androidx.annotation.StringRes
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.error.ServerException
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

data class ErrorMsg(private val msg: String? = null, @StringRes private val msgRes: Int = R.string.generic_error) {
	fun resolve(context: Context?): String = msg ?: context?.getString(msgRes) ?: ""
	
	companion object {
		fun fromThrowable(throwable: Throwable): ErrorMsg {
			Timber.e(throwable)
			val ex = throwable as? ServerException
			return when (ex) {
				is ServerException.HttpError -> ErrorMsg(
					ex.error
				)
				is ServerException.Network -> ErrorMsg(
					msgRes = R.string.no_connection
				)
				is ServerException.Unknown, null -> ErrorMsg(
					msgRes = R.string.generic_error
				)
			}
		}
	}
}

@Suppress("FunctionName")
inline fun ErrorHandler(crossinline block: (msg: ErrorMsg) -> Unit): CoroutineExceptionHandler =
	CoroutineExceptionHandler { _, throwable ->
		block(
			ErrorMsg.fromThrowable(
				throwable
			)
		)
	}
