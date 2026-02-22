package com.atl.bakuboutiquehub.homescreens.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val sliderHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable = Runnable {
        val count = binding.bannerViewPager.adapter?.itemCount ?: 0
        if (count > 0) {
            val nextItem = (binding.bannerViewPager.currentItem + 1) % count
            binding.bannerViewPager.currentItem = nextItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        handleBackPress()
        setupBanner()
        setupCategories()
        setUpNewIn()
        setUpTrending()

        if (isLoggedIn) {
            setupLoggedInUI()
            val name = arguments?.getString("user_nickname")
            binding.ad.text = name ?: "İstifadəçi"
        } else {
            setupLoggedOutUI()
        }

        binding.signupbutton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_signUpFragment)
        }

        binding.loginbutton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_loginFragment)
        }
    }

    private fun setUpNewIn(){
        val products = listOf(
            Product("Wedding dress", "$69.4", "45", "245", R.drawable.suggestion_pics),
            Product("Evening Gown", "$85.0", "12", "110", R.drawable.suggestion_pics),
            Product("Party Mini Dress", "$45.9", "28", "89", R.drawable.suggestion_pics),
            Product("Business Blazer", "$120.0", "5", "56", R.drawable.suggestion_pics),
            Product("Silk Gala Gown", "$150.0", "3", "320", R.drawable.suggestion_pics)
        )
        binding.rvNewIn.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNewIn.adapter = ProductAdapter(products)
    }

    private fun setUpTrending(){
        val trendingProducts = listOf(
            Product("Silk Gala Gown", "$150.0", "3", "320", R.drawable.suggestion_pics),
            Product("Business Blazer", "$120.0", "5", "56", R.drawable.suggestion_pics),
            Product("Classic Stiletto", "$89.9", "10", "430", R.drawable.suggestion_pics),
            Product("Luxury Handbag", "$210.0", "2", "15", R.drawable.suggestion_pics)
        )

        val trendingAdapter = ProductAdapter(trendingProducts)
        binding.rvTrending.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }
    }

    private fun setupCategories() {
        // 1. Data siyahısı (Şəkilləri öz drawable adlarınla dəyiş)
        val categoryList = listOf(
            Category(1, "Gala", "312 Collections", R.drawable._gala),
            Category(2, "Wedding", "231 Collections", R.drawable.wedding),
            Category(3, "Party", "65 Collections", R.drawable.party),
            Category(4, "Business", "20 Collections", R.drawable._business)
        )

        // 2. Adapteri qoşuruq
        val categoryAdapter = CategoryAdapter(categoryList)

        // 3. RecyclerView tənzimləmələri
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 2) // 2 sütunlu grid
            adapter = categoryAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false // NestedScrollView daxilində hamar işləməsi üçün
        }
    }

    private fun setupBanner() {
        val images = listOf(R.drawable.suggestion_pics, R.drawable.suggestion_pics, R.drawable.suggestion_pics)
        val adapter = BannerAdapter(images)
        binding.bannerViewPager.adapter = adapter

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(20))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        binding.bannerViewPager.setPageTransformer(compositePageTransformer)
        binding.bannerViewPager.offscreenPageLimit = 3

        TabLayoutMediator(binding.bannerIndicator, binding.bannerViewPager) { _, _ -> }.attach()
        startAutoSlider()
    }

    private fun startAutoSlider() {
        sliderHandler.removeCallbacks(sliderRunnable)
        sliderHandler.postDelayed(sliderRunnable, 3000)

        binding.bannerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
    }

    private fun setupLoggedInUI() {
        binding.buttonsContainer.visibility = View.GONE
        binding.dividerLine.visibility = View.GONE // Əlavə etdiyimiz barrier-li xətt
        binding.xosgelmisiniz.visibility = View.VISIBLE
        binding.ad.visibility = View.VISIBLE
    }

    private fun setupLoggedOutUI() {
        binding.buttonsContainer.visibility = View.VISIBLE
        binding.dividerLine.visibility = View.VISIBLE
        binding.xosgelmisiniz.visibility = View.GONE
        binding.ad.visibility = View.GONE
    }

    private fun clicksMore(){
        binding.moreNewIn.setOnClickListener {

        }
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderHandler.removeCallbacks(sliderRunnable)
        _binding = null
    }
}