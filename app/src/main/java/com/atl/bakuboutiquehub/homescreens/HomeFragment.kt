package com.atl.bakuboutiquehub.homescreens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        // Hər iki halda geri düyməsini idarə edirik ki, onboarding-ə qayıtmasın
        handleBackPress()

        if (isLoggedIn) {
            setupLoggedInUI()

            binding.xosgelmisiniz.visibility = View.VISIBLE
            // Login-dən gələn adı göstərmək üçün (əgər göndərilirsə)
            val name = arguments?.getString("user_nickname")
            binding.ad.text = name ?: "İstifadəçi"
            binding.ad.visibility = View.VISIBLE
        } else {
            setupLoggedOutUI()
        }

        // Qeydiyyat düyməsi - Yeni ID-yə görə düzəldildi
        binding.signupbutton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_signUpFragment)
        }

        // Giriş düyməsi - Yeni ID-yə görə düzəldildi
        binding.loginbutton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_loginFragment)
        }
    }

    private fun setupLoggedInUI() {
        binding.question.visibility = View.GONE
        binding.buttonsContainer.visibility = View.GONE
        binding.dividerLine.visibility = View.GONE
    }

    private fun setupLoggedOutUI() {
        binding.question.visibility = View.VISIBLE
        binding.buttonsContainer.visibility = View.VISIBLE
        binding.dividerLine.visibility = View.VISIBLE

        // Giriş etməyibsə xoş gəlmisiniz hissəsini gizlət
        binding.xosgelmisiniz.visibility = View.GONE
        binding.ad.visibility = View.GONE
    }

    private fun handleBackPress() {
        // Bu kod Home-da olanda geri düyməsinə basıldıqda tətbiqi bağlayır
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}