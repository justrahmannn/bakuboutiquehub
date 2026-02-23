package com.atl.bakuboutiquehub.homescreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.atl.bakuboutiquehub.databinding.FragmentLikeBinding
import com.atl.bakuboutiquehub.homescreens.home.ProductAdapter
import com.atl.bakuboutiquehub.network.db.SavedProductsManager

class LikeFragment : Fragment() {

    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val savedList = SavedProductsManager.getSavedProducts()

        // 1. Adapteri yaradarkən onFavoriteChanged funksiyasını ötürürük
        val adapter = ProductAdapter(
            productList = savedList,
            showSaveButton = true,
            onFavoriteChanged = {
                // Bu hissə ürək düyməsinə basılanda işə düşür
                setupRecyclerView() // Siyahını və ekranı dərhal təzələyirik
            }
        ) { product ->
            // Detal səhifəsinə keçid məntiqi bura
        }

        binding.rvSavedProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}