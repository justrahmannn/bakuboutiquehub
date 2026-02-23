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

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupNavigationLogic(navHostFragment)

        binding.bottomnav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productInfoFragment,
                R.id.moreNewInFragment,
                R.id.onBoarding1Fragment,
                R.id.onBoarding2Fragment,
                R.id.onBoarding3Fragment,
                R.id.loginFragment,
                R.id.signUpFragment,
                R.id.boutiqueorUserFragment2,
                R.id.registerBusinessInfoFragment,
                R.id.registerBusinessInfoItemFragment,
                R.id.customerInfoFragment -> {
                    binding.bottomnav.visibility = View.GONE
                }
                else -> {
                    binding.bottomnav.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupNavigationLogic(navHostFragment: NavHostFragment) {
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.app_nav)

        val appPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val isFirstTime = appPrefs.getBoolean("is_first_time", true)

        // Login olub-olmamasına baxmadan, Onboarding keçilibsə Home-a göndər
        val startDestId = if (isFirstTime) {
            R.id.onBoarding1Fragment
        } else {
            R.id.home // Artıq həmişə Home-dan başlayacaq
        }

        navGraph.setStartDestination(startDestId)
        navController.setGraph(navGraph, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}