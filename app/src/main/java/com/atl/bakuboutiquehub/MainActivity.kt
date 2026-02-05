package com.atl.bakuboutiquehub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.atl.bakuboutiquehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLastDestination()
    }

    private fun checkLastDestination() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isFirstTime = prefs.getBoolean("is_first_time", true)
        val lastFragmentId = prefs.getInt("last_fragment_id", R.id.onBoarding1Fragment)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.app_nav)

        // Start destination-ı təyin et
        val startDestination = if (isFirstTime) {
            R.id.onBoarding1Fragment
        } else {
            lastFragmentId
        }

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }

    override fun onPause() {
        super.onPause()
        // Hazırkı fragmenti yadda saxla
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

        val currentFragmentId = navHostFragment?.navController?.currentDestination?.id

        if (currentFragmentId != null) {
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            prefs.edit().putInt("last_fragment_id", currentFragmentId).apply()
        }
    }
}