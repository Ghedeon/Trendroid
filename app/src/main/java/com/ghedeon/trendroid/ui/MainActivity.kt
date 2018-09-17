package com.ghedeon.trendroid.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ghedeon.trendroid.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val navController = findNavController(R.id.nav_host_fragment)
		setupActionBarWithNavController(navController)
	}
	
	override fun onSupportNavigateUp() =
		findNavController(R.id.nav_host_fragment).navigateUp()
}
