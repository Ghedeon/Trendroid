@file:Suppress("HasPlatformType")

package com.ghedeon.trendroid.di

import com.ghedeon.trendroid.common.error.ErrorCallAdapterFactory
import com.ghedeon.trendroid.data.Api
import com.ghedeon.trendroid.data.GithubApi
import com.ghedeon.trendroid.data.GithubDataRepository
import com.ghedeon.trendroid.domain.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
			.addCallAdapterFactory(ErrorCallAdapterFactory.create(RxJava2CallAdapterFactory.create()))
			.build()
	}
}

@Module
abstract class OkHttpBuilderModule { //TODO
	
	@Module
	companion object {
		
		@Provides
		@Singleton
		@JvmStatic
		fun provideOkHttpClientBuilder() = OkHttpClient.Builder()
			.followRedirects(true)
			.followSslRedirects(true)
	}
}

@Module(includes = [OkHttpBuilderModule::class])
abstract class OkHttpModule {
	
	@Module
	companion object {
		
		@Provides
		@Singleton
		@JvmStatic
		fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
			val loggingInterceptor = HttpLoggingInterceptor() //TODO
			loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
			builder.addInterceptor(loggingInterceptor)
			return builder.build()
		}
	}
}