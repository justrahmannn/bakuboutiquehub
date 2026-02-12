package com.atl.bakuboutiquehub.onboardingscreens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentOnBoarding3Binding

class OnBoarding3Fragment : Fragment() {

    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.app_nav, true)
            .build()

        binding.startButton.setOnClickListener {
            saveOnboardingCompleted()
            findNavController().navigate(
                R.id.action_onBoarding3Fragment_to_home,
                null,
                navOptions
            )
        }

        binding.loginLink.setOnClickListener {
            saveOnboardingCompleted()
            findNavController().navigate(
                R.id.action_onBoarding3Fragment_to_loginFragment,
                null,
                navOptions
            )
        }

        binding.signupLink.setOnClickListener {
            saveOnboardingCompleted()
            findNavController().navigate(
                R.id.action_onBoarding3Fragment_to_signUpFragment,
                null,
                navOptions
            )
        }
    }

    private fun saveOnboardingCompleted() {
        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("is_first_time", false).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}