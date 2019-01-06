// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
	id("com.github.ben-manes.versions") version "0.20.0"
}

buildscript {
	repositories {
		google()
		jcenter()
	}
	
	dependencies {
		classpath(deps.gradle_plugin)
		classpath(deps.kotlin.plugin)
		classpath(deps.navigation.safe_args_plugin)
	}
}

allprojects {
	repositories {
		google()
		jcenter()
	}
}

task("clean", Delete::class) {
	delete = setOf(rootProject.buildDir)
}