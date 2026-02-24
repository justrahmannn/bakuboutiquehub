package com.atl.bakuboutiquehub.homescreens.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.atl.bakuboutiquehub.databinding.FragmentProductInfoBinding

class ProductInfoFragment : Fragment() {

    private var _binding: FragmentProductInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Məlumatları Bundle-dan alırıq (MoreNewInFragment-dən göndərilən key-lər)
        val name = arguments?.getString("p_name")
        val price = arguments?.getString("p_price")
        val rating = arguments?.getString("p_rating")
        val reviews = arguments?.getString("p_reviews")
        val imageRes = arguments?.getInt("p_image") ?: 0

        // 2. Məlumatları XML ID-lərinə uyğun olaraq yerləşdiririk
        binding.apply {
            tvProductName.text = name
            tvPrice.text = price // XML-də id: tvPrice
            // RatingBar-ı da yeniləyək
            ratingBar.rating = rating?.toFloatOrNull() ?: 0f

            // Şəkli Hero Image hissəsinə qoyuruq
            if (imageRes != 0) {
                ivProductHero.setImageResource(imageRes) // XML-də id: ivProductHero
            }
        }

        // 3. Geri düyməsi (XML-də id: btnBack)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 4. Zəng funksiyası
        binding.tvCallLink.setOnClickListener {
            val phoneNumber = binding.tvPhone.text.toString()
            if (phoneNumber.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}