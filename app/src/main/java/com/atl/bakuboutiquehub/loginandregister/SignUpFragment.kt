package com.atl.bakuboutiquehub.loginandregister

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.atl.bakuboutiquehub.network.RetrofitClient
import com.atl.bakuboutiquehub.network.model.SignupRequest
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.googleButton.setOnClickListener {
            signInWithGoogle()
        }

        binding.nextButton.setOnClickListener {
            if (isInputValid()) {
                showCustomTermsDialog()
            }
        }

        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun signInWithGoogle() {
        Toast.makeText(requireContext(), "Google ilə qeydiyyat tezliklə aktiv olacaq", Toast.LENGTH_SHORT).show()
    }

    private fun isInputValid(): Boolean {
        val email = binding.emailInput.text.toString().trim()
        val phone = binding.phoneInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

        clearErrors()

        return when {
            email.isEmpty() -> {
                binding.emailInput.error = "E-poçt boş ola bilməz"
                binding.emailInput.requestFocus()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInput.error = "Düzgün e-poçt formatı daxil edin"
                binding.emailInput.requestFocus()
                false
            }
            phone.isEmpty() -> {
                binding.phoneInput.error = "Mobil nömrə daxil edin"
                binding.phoneInput.requestFocus()
                false
            }
            phone.length < 9 -> {
                binding.phoneInput.error = "Nömrə çox qısadır"
                binding.phoneInput.requestFocus()
                false
            }
            password.isEmpty() -> {
                binding.passwordInput.error = "Parol daxil edin"
                binding.passwordInput.requestFocus()
                false
            }
            password.length < 6 -> {
                binding.passwordInput.error = "Parol minimum 6 simvol olmalıdır"
                binding.passwordInput.requestFocus()
                false
            }
            confirmPassword != password -> {
                binding.confirmPasswordInput.error = "Parollar eyni deyil"
                binding.confirmPasswordInput.requestFocus()
                false
            }
            !binding.termsCheckbox.isChecked -> {
                Toast.makeText(requireContext(), "İstifadəçi şərtlərini qəbul etməlisiniz", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun showCustomTermsDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_terms, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<Button>(R.id.btnAccept).setOnClickListener {
            binding.termsCheckbox.isChecked = true
            alertDialog.dismiss()
            completeRegistration()
        }

        dialogView.findViewById<Button>(R.id.btnReject).setOnClickListener {
            binding.termsCheckbox.isChecked = false
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun completeRegistration() {
        val email = binding.emailInput.text.toString().trim()
        val phone = binding.phoneInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val fullName = email.substringBefore("@") // Placeholder for full name if not in UI

        val signupRequest = SignupRequest(
            email = email,
            password = password,
            fullName = fullName,
            phoneNumber = phone
        )

        lifecycleScope.launch {
            try {
                binding.nextButton.isEnabled = false
                val response = RetrofitClient.authService.register(signupRequest)

                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Qeydiyyat uğurla tamamlandı!", Toast.LENGTH_SHORT).show()
                    
                    val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    sharedPref.edit().putBoolean("isLoggedIn", true).apply()

                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.signUpFragment, true)
                        .build()

                    val bundle = Bundle().apply {
                        putString("user_email", email)
                    }

                    findNavController().navigate(R.id.action_signUpFragment_to_boutiqueorUserFragment2, bundle, navOptions)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Xəta baş verdi"
                    Toast.makeText(requireContext(), "Xəta: $errorMsg", Toast.LENGTH_LONG).show()
                    binding.nextButton.isEnabled = true
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Bağlantı xətası: ${e.message}", Toast.LENGTH_LONG).show()
                binding.nextButton.isEnabled = true
            }
        }
    }

    private fun clearErrors() {
        binding.emailInput.error = null
        binding.phoneInput.error = null
        binding.passwordInput.error = null
        binding.confirmPasswordInput.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}