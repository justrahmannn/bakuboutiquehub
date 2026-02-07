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
        _binding = FragmentCustomerInfoBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            validateAndProceed()
            Toast.makeText(requireContext(), "Qeydiyyat uğurla tamamlandı", Toast.LENGTH_SHORT).show()

        }
    }

    private fun validateAndProceed() {
        val name = binding.etName.text.toString().trim()
        val surname = binding.etSurname.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()

        clearErrors()

        when {
            name.isEmpty() -> {
                binding.tilName.error = "Adınızı daxil edin"
            }
            surname.isEmpty() -> {
                binding.tilSurname.error = "Soyadınızı daxil edin"
            }
            username.isEmpty() -> {
                binding.tilUsername.error = "İstifadəçi adı daxil edin"
            }
            username.length < 3 -> {
                binding.tilUsername.error = "İstifadəçi adı çox qısadır"
            }
            else -> {
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

        findNavController().navigate(
            R.id.action_customerInfoFragment_to_homeFragment, bundle
        )
    }

    private fun clearErrors() {
        binding.tilName.error = null
        binding.tilSurname.error = null
        binding.tilUsername.error = null
    }
}