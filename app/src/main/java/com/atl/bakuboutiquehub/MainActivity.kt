package com.atl.bakuboutiquehub

import android.content.Context
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
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // NavHostFragment-i tap
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            ?: return // Əgər tapılmadısa geri qayıt

        val navController = navHostFragment.navController

        // Əgər istifadəçi daxil olubsa
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            try {
                // Graph artıq activity_main.xml-də set olunub, yenidən set etməyə ehtiyac yoxdur
                navController.navigate(R.id.homeFragment)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Əks halda, default start destination-da qalacaq (nav_graph.xml-dəki startDestination)
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