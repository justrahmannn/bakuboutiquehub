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
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentSignUpBinding

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
                binding.emailInputLayout.error = "E-poçt boş ola bilməz"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInputLayout.error = "Düzgün e-poçt formatı daxil edin"
                false
            }
            phone.isEmpty() -> {
                binding.phoneInputLayout.error = "Mobil nömrə daxil edin"
                false
            }
            phone.length < 9 -> {
                binding.phoneInputLayout.error = "Nömrə çox qısadır"
                false
            }
            password.isEmpty() -> {
                binding.passwordInputLayout.error = "Parol daxil edin"
                false
            }
            password.length < 6 -> {
                binding.passwordInputLayout.error = "Parol minimum 6 simvol olmalıdır"
                false
            }
            confirmPassword != password -> {
                binding.confirmPasswordInputLayout.error = "Parollar eyni deyil"
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
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("isLoggedIn", true).apply()


        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.signUpFragment, true)
            .build()

        val bundle = Bundle()
        val userEmail = binding.emailInput.text.toString().trim()
        bundle.putString("user_email", userEmail)


        findNavController().navigate(R.id.action_signUpFragment_to_boutiqueorUserFragment2, bundle, navOptions)
    }

    private fun clearErrors() {
        binding.emailInputLayout.error = null
        binding.phoneInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordInputLayout.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}