package com.ghedeon.trendroid.common.error


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ErrorCallAdapterFactory(private val originalFactory: CoroutineCallAdapterFactory) : CallAdapter.Factory() {
	
	override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> =
		InternalCallAdapter(originalFactory.get(returnType, annotations, retrofit))
	
	private class InternalCallAdapter<RESPONSE, TYPE>(val originalAdapter: CallAdapter<RESPONSE, TYPE>?) :
		CallAdapter<RESPONSE, TYPE> {
		private val errorMapper = { throwable: Throwable -> ServerException.from(throwable) }
		
		override fun responseType() = originalAdapter?.responseType()
		
		@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
		override fun adapt(originalCall: Call<RESPONSE>): TYPE? {
			if (originalAdapter == null) return null
			
			return try {
				originalAdapter.adapt(originalCall)
			} catch (t: Throwable) {
				throw errorMapper(t)
			}
		}
	}
}
