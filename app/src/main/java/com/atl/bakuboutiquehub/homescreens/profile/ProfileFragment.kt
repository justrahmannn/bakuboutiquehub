package com.atl.bakuboutiquehub.homescreens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentHomeBinding
import com.atl.bakuboutiquehub.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutUnauth.btnLoginTrigger.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_loginFragment)
        }
        binding.layoutUnauth.tvSignupLink.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_signUpFragment)
        }

    }
}