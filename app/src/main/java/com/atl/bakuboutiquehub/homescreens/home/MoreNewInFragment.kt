package com.atl.bakuboutiquehub.homescreens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentMoreNewInBinding

class MoreNewInFragment : Fragment() {

    private var _binding: FragmentMoreNewInBinding? = null
    private val binding get() = _binding!!

    // Orijinal siyahı filtr və sıralama zamanı məlumat itkisinin qarşısını alır
    private var originalProductList = listOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreNewInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = arguments?.getString("category_type") ?: "NEW_IN"
        val screenTitle = arguments?.getString("screen_title") ?: "New in"
        binding.newInOrTrending.text = screenTitle

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        // FİLTER DÜYMƏSİ
        binding.btnFilter.setOnClickListener {
            val sortSheet = SortBottomSheet(
                onApply = { min, max, nameS, priceS ->
                    filterAndSortAll(min, max, nameS, priceS)
                },
                onReset = {
                    // Orijinal siyahını bərpa et
                    updateAdapter(originalProductList)
                }
            )
            sortSheet.show(childFragmentManager, "SortBottomSheet")
        }

        setupRecyclerView(type)
    }

    private fun setupRecyclerView(type: String?) {
        originalProductList = if (type == "NEW_IN") getNewInProducts() else getTrendingProducts()
        updateAdapter(originalProductList)
    }

    private fun filterAndSortAll(min: Float, max: Float, nameSort: String?, priceSort: String?) {
        // 1. Qiymət aralığına görə filterləmə
        var list = originalProductList.filter { product ->
            val p = product.price.replace(Regex("[^\\d.]"), "").toFloatOrNull() ?: 0f
            p in min..max
        }

        // 2. Qiymət sıralaması tətbiqi
        list = when (priceSort) {
            "LOW_HIGH" -> list.sortedBy { it.price.replace(Regex("[^\\d.]"), "").toFloatOrNull() }
            "HIGH_LOW" -> list.sortedByDescending { it.price.replace(Regex("[^\\d.]"), "").toFloatOrNull() }
            else -> list
        }

        // 3. Ad sıralaması tətbiqi
        list = when (nameSort) {
            "AZ" -> list.sortedBy { it.name }
            "ZA" -> list.sortedByDescending { it.name }
            else -> list
        }

        updateAdapter(list)
    }

    private fun updateAdapter(list: List<Product>) {
        binding.tvProductCount.text = "${list.size} Products"

        val productAdapter = ProductAdapter(list, true) { selectedProduct ->
            // Kliklənən məhsulun məlumatlarını Bundle-a qoyuruq
            val bundle = Bundle().apply {
                putString("p_name", selectedProduct.name)
                putString("p_price", selectedProduct.price)
                putString("p_stock", selectedProduct.stock)
                putString("p_reviews", selectedProduct.reviews)
                putInt("p_image", selectedProduct.imageRes)
            }
            // Naviqasiya zamanı bundle-ı ötürürük
            findNavController().navigate(R.id.action_moreNewInFragment_to_productInfoFragment, bundle)
        }

        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    // --- MOCK DATA ---
    private fun getNewInProducts() = listOf(
        Product("Wedding dress", "$69.4", "45", "245", R.drawable.suggestion_pics),
        Product("Evening Gown", "$85.0", "12", "110", R.drawable.suggestion_pics),
        Product("Party Mini Dress", "$45.9", "28", "89", R.drawable.suggestion_pics),
        Product("Business Blazer", "$120.0", "5", "56", R.drawable.suggestion_pics),
        Product("Silk Gala Gown", "$150.0", "3", "320", R.drawable.suggestion_pics),
        Product("Velvet Night Suit", "$115.0", "8", "67", R.drawable.suggestion_pics),
        Product("Summer Floral Skirt", "$34.9", "30", "156", R.drawable.suggestion_pics),
        Product("Classic Trench Coat", "$145.0", "4", "42", R.drawable.suggestion_pics),
        Product("Diamond Stud Earrings", "$89.0", "15", "210", R.drawable.suggestion_pics),
        Product("Leather Handbag", "$199.9", "2", "34", R.drawable.suggestion_pics),
        Product("High-Heel Sandals", "$75.0", "12", "88", R.drawable.suggestion_pics),
        Product("Boho Style Blouse", "$42.5", "22", "112", R.drawable.suggestion_pics)
    )

    private fun getTrendingProducts() = listOf(
        Product("Silk Gala Gown", "$150.0", "3", "320", R.drawable.suggestion_pics),
        Product("Business Blazer", "$120.0", "5", "56", R.drawable.suggestion_pics),
        Product("Classic Stiletto", "$89.9", "10", "430", R.drawable.suggestion_pics),
        Product("Luxury Handbag", "$210.0", "2", "15", R.drawable.suggestion_pics),
        Product("Gold Chain Necklace", "$45.0", "15", "85", R.drawable.suggestion_pics),
        Product("Designer Sunglasses", "$130.0", "8", "120", R.drawable.suggestion_pics),
        Product("Velvet Evening Clutch", "$65.0", "12", "42", R.drawable.suggestion_pics),
        Product("Satin Wrap Dress", "$95.5", "6", "110", R.drawable.suggestion_pics),
        Product("Leather Ankle Boots", "$110.0", "4", "95", R.drawable.suggestion_pics),
        Product("Pearl Drop Earrings", "$35.0", "25", "215", R.drawable.suggestion_pics),
        Product("Classic Wristwatch", "$180.0", "3", "56", R.drawable.suggestion_pics),
        Product("Wool Winter Coat", "$220.0", "2", "38", R.drawable.suggestion_pics)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}