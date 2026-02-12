package com.atl.bakuboutiquehub.loginandregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentCustomerInfoBinding

class CustomerInfoFragment : Fragment() {

    private var _binding: FragmentCustomerInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val role = arguments?.getString("role")

        // Role "customer" olduqda butiq sahələrini gizlət və düyməni yuxarı çək
        if (role == "customer") {
            binding.tvLabelShopName.visibility = View.GONE
            binding.cardShopName.visibility = View.GONE
            binding.tvLabelVerification.visibility = View.GONE
            binding.cardVerificationCode.visibility = View.GONE

            // Düymənin constraint-ini dəyişərək İstifadəçi adı sahəsinin altına bağlayırıq
            val params = binding.btnNext.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
            params.topToBottom = binding.cardUsername.id
            binding.btnNext.layoutParams = params
        }

        binding.btnNext.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun validateAndProceed() {
        val name = binding.etName.text.toString().trim()
        val surname = binding.etSurname.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()

        // Hər dəfə yoxlamadan əvvəl error-ları təmizləyirik
        clearErrors()

        when {
            name.isEmpty() -> {
                binding.etName.error = "Adınızı daxil edin"
                binding.etName.requestFocus()
            }
            surname.isEmpty() -> {
                binding.etSurname.error = "Soyadınızı daxil edin"
                binding.etSurname.requestFocus()
            }
            username.isEmpty() -> {
                binding.etUsername.error = "İstifadəçi adı daxil edin"
                binding.etUsername.requestFocus()
            }
            username.length < 3 -> {
                binding.etUsername.error = "İstifadəçi adı çox qısadır"
                binding.etUsername.requestFocus()
            }
            else -> {
                Toast.makeText(requireContext(), "Məlumatlar təsdiqləndi", Toast.LENGTH_SHORT).show()
                navigateToNextScreen(name, surname, username)
            }
        }
    }

    private fun navigateToNextScreen(name: String, surname: String, username: String) {
        val bundle = Bundle().apply {
            putString("first_name", name)
            putString("last_name", surname)
            putString("user_nickname", username)
        }

        // Qeyd: action ID-nin nav_graph-da düzgün olduğundan əmin ol
        findNavController().navigate(
            R.id.action_customerInfoFragment_to_home, bundle
        )
    }

    private fun clearErrors() {
        binding.etName.error = null
        binding.etSurname.error = null
        binding.etUsername.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}