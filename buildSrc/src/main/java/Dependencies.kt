object versions {
	val gradle_plugin = "3.2.1"
	val kotlin = "1.3.11"
	val coroutines = "1.1.0"
	val androidx = "1.0.0"
	val lifecycle = "2.1.0-alpha01"
	val constraint_layout = "1.1.3"
	val navigation = "1.0.0-alpha09"
	val dagger = "2.16"
	val okhttp = "3.12.1"
	val retrofit = "2.5.0"
	val epoxy = "3.1.0"
	val glide = "4.8.0"
	val jsoup = "1.11.3"
	val videoview = "1.0.4"
	val timber = "4.7.1"
}

object deps {
	val gradle_plugin = "com.android.tools.build:gradle:${versions.gradle_plugin}"
	
	object kotlin {
		val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${versions.kotlin}"
		val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"
		val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}"
		val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.coroutines}"
	}
	
	object androidx {
		val appcompat = "androidx.appcompat:appcompat:${versions.androidx}"
		val core = "androidx.core:core-ktx:1.1.0-alpha03"
		val cardview = "androidx.cardview:cardview:${versions.androidx}"
		val recyclerview = "androidx.recyclerview:recyclerview:${versions.androidx}"
		val fragment = "androidx.fragment:fragment-ktx:1.1.0-alpha03"
	}
	
	val constraint_layout = "androidx.constraintlayout:constraintlayout:${versions.constraint_layout}"
	
	object lifecycle {
		val extensions = "androidx.lifecycle:lifecycle-extensions:${versions.lifecycle}"
		val java8 = "androidx.lifecycle:lifecycle-common-java8:${versions.lifecycle}"
		val shopify_livedata_ktx = "com.shopify:livedata-ktx:2.0.1"
		val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:2.1.0-alpha01"
	}
	
	object navigation {
		val fragment = "android.arch.navigation:navigation-fragment-ktx:${versions.navigation}"
		val ui = "android.arch.navigation:navigation-ui-ktx:${versions.navigation}"
		val safe_args_plugin = "android.arch.navigation:navigation-safe-args-gradle-plugin:${versions.navigation}"
	}
	
	object dagger {
		val runtime = "com.google.dagger:dagger:${versions.dagger}"
		val android = "com.google.dagger:dagger-android:${versions.dagger}"
		val support = "com.google.dagger:dagger-android-support:${versions.dagger}"
		val compiler = "com.google.dagger:dagger-compiler:${versions.dagger}"
		val processor = "com.google.dagger:dagger-android-processor:${versions.dagger}"
	}
	
	object okhttp {
		val core = "com.squareup.okhttp3:okhttp:${versions.okhttp}"
		val logging = "com.squareup.okhttp3:logging-interceptor:${versions.okhttp}"
	}
	
	object retrofit {
		val runtime = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
		val moshi_converter = "com.squareup.retrofit2:converter-moshi:${versions.retrofit}"
		val coroutines_adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
	}
	
	object epoxy {
		val runtime = "com.airbnb.android:epoxy:${versions.epoxy}"
		val processor = "com.airbnb.android:epoxy-processor:${versions.epoxy}"
	}
	
	object glide {
		val runtime = "com.github.bumptech.glide:glide:${versions.glide}"
		val compiler = "com.github.bumptech.glide:compiler:${versions.glide}"
		
	}
	
	val jsoup = "org.jsoup:jsoup:${versions.jsoup}"
	val videoview = "com.yqritc:android-scalablevideoview:${versions.videoview}"
	val timber = "com.jakewharton.timber:timber:${versions.timber}"
}
