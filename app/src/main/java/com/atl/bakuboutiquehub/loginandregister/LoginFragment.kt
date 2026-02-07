package com.atl.bakuboutiquehub.loginandregister

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentLoginBinding // XML adınıza uyğun binding sinfi

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

        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null

        when {
            email.isEmpty() -> {
                binding.emailInputLayout.error = "E-poçt daxil edin"
                binding.emailInput.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInputLayout.error = "Düzgün e-poçt formatı daxil edin"
                binding.emailInput.requestFocus()
            }
            password.isEmpty() -> {
                binding.passwordInputLayout.error = "Parolu daxil edin"
                binding.passwordInput.requestFocus()
            }
            password.length < 6 -> {
                binding.passwordInputLayout.error = "Parol minimum 6 simvol olmalıdır"
                binding.passwordInput.requestFocus()
            }
            else -> {
                Toast.makeText(requireContext(), "Giriş uğurludur!", Toast.LENGTH_SHORT).show()

                val bundle = Bundle()
                val userEmail = binding.emailInput.text.toString()

                bundle.putString("user_email", userEmail)

                findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
            }
        }

        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", true)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}