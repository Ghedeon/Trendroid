@file:Suppress("HasPlatformType")

package com.ghedeon.trendroid.di

import com.ghedeon.trendroid.common.error.ErrorCallAdapterFactory
import com.ghedeon.trendroid.data.Api
import com.ghedeon.trendroid.data.GithubApi
import com.ghedeon.trendroid.data.GithubDataRepository
import com.ghedeon.trendroid.domain.GithubRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpModule::class])
abstract class DataModule {
	
	@Binds
	@Singleton
	abstract fun bindGithubRepository(repository: GithubDataRepository): GithubRepository
	
	@Module
	companion object {
		
		@Provides
		@Singleton
		@JvmStatic
		fun provideGithubApi(retrofit: Retrofit) = retrofit.create(GithubApi::class.java)
		
		@Provides
		@Singleton
		@JvmStatic
		fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(Api.API_URL)
			.addConverterFactory(MoshiConverterFactory.create())
			.addCallAdapterFactory(ErrorCallAdapterFactory(CoroutineCallAdapterFactory()))
			.build()
	}
}

@Module
abstract class OkHttpBuilderModule {
	
	@Module
	companion object {
		
		@Provides
		@Singleton
		@JvmStatic
		fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
			val loggingInterceptor = HttpLoggingInterceptor()
			loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
			return OkHttpClient.Builder()
				.followRedirects(true)
				.followSslRedirects(true)
				.addInterceptor(loggingInterceptor)
		}
	}
}

@Module(includes = [OkHttpBuilderModule::class])
abstract class OkHttpModule {
	
	@Module
	companion object {
		
		@Provides
		@Singleton
		@JvmStatic
		fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient = builder.build()
	}
}