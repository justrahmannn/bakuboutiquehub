package com.atl.bakuboutiquehub

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.atl.bakuboutiquehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomnav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.boutiqueorUserFragment2,
                R.id.registerBusinessInfoFragment,
                R.id.registerBusinessInfoItemFragment,
                R.id.onBoarding1Fragment,
                R.id.onBoarding2Fragment,
                R.id.onBoarding3Fragment,
                R.id.customerInfoFragment,
                R.id.signUpFragment -> {
                    binding.bottomnav.visibility = View.GONE
                }
                else -> {
                    binding.bottomnav.visibility = View.VISIBLE
                }
            }
        }

        checkLastDestination()


    }

    private fun checkLastDestination() {
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment ?: return
        val navController = navHostFragment.navController

        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            navController.navigate(R.id.homeFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragmentId = navHostFragment?.navController?.currentDestination?.id

        if (currentFragmentId != null) {
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            prefs.edit().putInt("last_fragment_id", currentFragmentId).apply()
        }
    }
}