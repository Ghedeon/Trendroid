package com.ghedeon.trendroid.common

import io.reactivex.Maybe


fun <T> List<T>?.asMaybe(): Maybe<List<T>> = if (this == null) Maybe.empty() else Maybe.just(this)