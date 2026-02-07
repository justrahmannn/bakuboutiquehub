package com.atl.bakuboutiquehub.loginandregister

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentBoutiqueorUserBinding
import com.atl.bakuboutiquehub.databinding.FragmentLoginBinding

class BoutiqueorUserFragment : Fragment() {

    private var _binding: FragmentBoutiqueorUserBinding? = null
    private val binding get() = _binding!!
    private var selectedRole = "" // "customer" və ya "owner"


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBoutiqueorUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ekran açılan kimi heç bir funksiya çağırılmır, selectedRole boşdur.

        binding.cardCustomer.setOnClickListener {
            selectRole("customer")
        }

        binding.cardOwner.setOnClickListener {
            selectRole("owner")
        }

        binding.nextbutton.setOnClickListener {
            // Seçim edilib-edilmədiyini burada yoxlayırıq
            if (selectedRole.isEmpty()) {
                Toast.makeText(context, "Zəhmət olmasa seçim edin", Toast.LENGTH_SHORT).show()
            } else {
                // Seçimə uyğun keçid
                if (selectedRole == "customer") {
                    findNavController().navigate(R.id.action_boutiqueorUserFragment2_to_customerInfoFragment)
                } else {
                    findNavController().navigate(R.id.action_boutiqueorUserFragment2_to_boutiqueInfoFragment)
                }
            }
        }
    }

    private fun selectRole(role: String) {
        selectedRole = role
        if (role == "customer") {
            // Müştəri seçildi: Yaşıl
            binding.cardCustomer.strokeColor = Color.parseColor("#4CAF50")
            binding.cardCustomer.strokeWidth = 5

            // Butiq sahibi: Boz
            binding.cardOwner.strokeColor = Color.parseColor("#D3D3D3")
            binding.cardOwner.strokeWidth = 2
        } else {
            // Butiq sahibi seçildi: Yaşıl
            binding.cardOwner.strokeColor = Color.parseColor("#4CAF50")
            binding.cardOwner.strokeWidth = 5

            // Müştəri: Boz
            binding.cardCustomer.strokeColor = Color.parseColor("#D3D3D3")
            binding.cardCustomer.strokeWidth = 2
        }
    }


}