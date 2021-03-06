import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import deps.dagger.android
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
	id("com.android.application")
	kotlin("android.extensions")
	kotlin("android")
	kotlin("kapt")
	id("androidx.navigation.safeargs")
}

android {
	compileSdkVersion(28)
	defaultConfig {
		applicationId = "com.ghedeon.trendroid"
		minSdkVersion(16)
		targetSdkVersion(28)
		versionCode = 1
		versionName = "1.0"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
		}
	}
	
	buildTypes.forEach {
		it.buildConfigField("String", "GITHUB_URL", "\"https://github.com/\"")
		it.buildConfigField("String", "GITHUB_API_URL", "\"https://api.github.com/\"")
	}
	
	androidExtensions {
		// https://github.com/gradle/kotlin-dsl/issues/644
		configure(delegateClosureOf<AndroidExtensionsExtension> {
			isExperimental = true
		})
	}
	
	compileOptions {
		setSourceCompatibility(JavaVersion.VERSION_1_8)
		setTargetCompatibility(JavaVersion.VERSION_1_8)
	}
}

kapt {
	correctErrorTypes = true
}

dependencies {
	implementation(deps.kotlin.stdlib)
	implementation(deps.kotlin.coroutines_core)
	implementation(deps.kotlin.coroutines_android)
	
	implementation(deps.androidx.appcompat)
	implementation(deps.androidx.core)
	implementation(deps.androidx.cardview)
	implementation(deps.androidx.recyclerview)
	implementation(deps.androidx.fragment)
	
	implementation(deps.lifecycle.extensions)
	implementation(deps.lifecycle.java8)
	implementation(deps.lifecycle.shopify_livedata_ktx)
	implementation(deps.lifecycle.livedata_ktx)
	
	implementation(deps.constraint_layout)
	
	implementation(deps.navigation.fragment)
	implementation(deps.navigation.ui)
	
	implementation(deps.dagger.runtime)
	implementation(deps.dagger.android)
	implementation(deps.dagger.support)
	
	implementation(deps.okhttp.core)
	implementation(deps.okhttp.logging)
	implementation(deps.retrofit.runtime)
	implementation(deps.retrofit.moshi_converter)
	implementation(deps.retrofit.coroutines_adapter)
	
	implementation(deps.epoxy.runtime)
	implementation(deps.glide.runtime)
	implementation(deps.jsoup)
	implementation(deps.videoview)
	implementation(deps.timber)
	
	kapt(deps.dagger.compiler)
	kapt(deps.dagger.processor)
	kapt(deps.epoxy.processor)
	kapt(deps.glide.compiler)
}
