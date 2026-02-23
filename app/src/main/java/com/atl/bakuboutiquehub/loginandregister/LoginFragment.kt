package com.atl.bakuboutiquehub.loginandregister

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentLoginBinding
import com.atl.bakuboutiquehub.network.RetrofitClient
import com.atl.bakuboutiquehub.network.model.LoginRequest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            validateAndLogin()
        }

        binding.googleButton.setOnClickListener {
            signInWithGoogle()
        }

        binding.signupLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun signInWithGoogle() {
        Toast.makeText(requireContext(), "Google ilə giriş tezliklə aktiv olacaq", Toast.LENGTH_SHORT).show()
    }

    private fun validateAndLogin() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        binding.emailInput.error = null
        binding.passwordInput.error = null

        when {
            email.isEmpty() -> {
                binding.emailInput.error = "E-poçt daxil edin"
                binding.emailInput.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInput.error = "Düzgün e-poçt formatı daxil edin"
                binding.emailInput.requestFocus()
            }
            password.isEmpty() -> {
                binding.passwordInput.error = "Parolu daxil edin"
                binding.passwordInput.requestFocus()
            }
            password.length < 6 -> {
                binding.passwordInput.error = "Parol minimum 6 simvol olmalıdır"
                binding.passwordInput.requestFocus()
            }
            else -> {
                val loginRequest = LoginRequest(email, password)

                lifecycleScope.launch {
                    try {
                        binding.loginButton.isEnabled = false
                        val response = RetrofitClient.authService.login(loginRequest)

                        if (response.isSuccessful) {
                            val authResponse = response.body()
                            val token = authResponse?.token

                            val sharedPref = requireActivity().getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putBoolean("isLoggedIn", true)
                                putString("user_email", email)
                                putString("auth_token", token)
                                apply()
                            }

                            Toast.makeText(requireContext(), "Giriş uğurludur!", Toast.LENGTH_SHORT).show()

                            val bundle = Bundle().apply {
                                putString("user_email", email)
                            }
                            findNavController().navigate(R.id.action_loginFragment_to_home, bundle)
                        } else {
                            val errorMsg = response.errorBody()?.string() ?: "Giriş uğursuzdur"
                            Toast.makeText(requireContext(), "Xəta: $errorMsg", Toast.LENGTH_LONG).show()
                            binding.loginButton.isEnabled = true
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Bağlantı xətası: ${e.message}", Toast.LENGTH_LONG).show()
                        binding.loginButton.isEnabled = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}