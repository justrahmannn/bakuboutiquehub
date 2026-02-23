package com.atl.bakuboutiquehub.homescreens.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.FilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class SortBottomSheet(
    private val onApply: (min: Float, max: Float, nameSort: String?, priceSort: String?) -> Unit,
    private val onReset: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    // İki ayrı qrup üçün seçimlər
    private var selectedNameSort: String? = null
    private var selectedPriceSort: String? = null

//    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // İlkin olaraq hamısını ağ edirik
        resetButtonVisuals()
        setupPriceSync()
        setupSortSelection()

        // TƏTBİQ ET
        binding.btnApply.setOnClickListener {
            val min = binding.etMinPrice.text.toString().toFloatOrNull() ?: binding.rangeSlider.valueFrom
            val max = binding.etMaxPrice.text.toString().toFloatOrNull() ?: binding.rangeSlider.valueTo
            onApply(min, max, selectedNameSort, selectedPriceSort)
            dismiss()
        }

        // SIFIRLA
        binding.btnReset.setOnClickListener {
            resetUI()
            onReset()
            dismiss()
        }
    }

    private fun setupPriceSync() {
        binding.rangeSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            if (binding.etMinPrice.text.toString() != values[0].toInt().toString()) {
                binding.etMinPrice.setText(values[0].toInt().toString())
            }
            if (binding.etMaxPrice.text.toString() != values[1].toInt().toString()) {
                binding.etMaxPrice.setText(values[1].toInt().toString())
            }
        }

        binding.etMinPrice.doAfterTextChanged { text ->
            val input = text.toString().toFloatOrNull()
            if (input != null && input >= binding.rangeSlider.valueFrom && input <= binding.rangeSlider.values[1]) {
                binding.rangeSlider.values = listOf(input, binding.rangeSlider.values[1])
            }
        }

        binding.etMaxPrice.doAfterTextChanged { text ->
            val input = text.toString().toFloatOrNull()
            if (input != null && input <= binding.rangeSlider.valueTo && input >= binding.rangeSlider.values[0]) {
                binding.rangeSlider.values = listOf(binding.rangeSlider.values[0], input)
            }
        }
    }

    private fun setupSortSelection() {
        // 1. AD QRUPU
        binding.btnAtoZ.setOnClickListener { handleNameSelection("AZ") }
        binding.btnZtoA.setOnClickListener { handleNameSelection("ZA") }

        // 2. QİYMƏT QRUPU
        binding.btnPriceLowHigh.setOnClickListener { handlePriceSelection("LOW_HIGH") }
        binding.btnPriceHighLow.setOnClickListener { handlePriceSelection("HIGH_LOW") }
    }

    private fun handleNameSelection(type: String) {
        selectedNameSort = if (selectedNameSort == type) null else type
        setButtonStyle(binding.btnAtoZ as MaterialButton, selectedNameSort == "AZ")
        setButtonStyle(binding.btnZtoA as MaterialButton, selectedNameSort == "ZA")
    }

    private fun handlePriceSelection(type: String) {
        selectedPriceSort = if (selectedPriceSort == type) null else type
        setButtonStyle(binding.btnPriceLowHigh as MaterialButton, selectedPriceSort == "LOW_HIGH")
        setButtonStyle(binding.btnPriceHighLow as MaterialButton, selectedPriceSort == "HIGH_LOW")
    }

    private fun setButtonStyle(button: MaterialButton, isActive: Boolean) {
        if (isActive) {
            button.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#1A1A1A"))
            button.setTextColor(Color.WHITE)
            button.strokeWidth = 0
        } else {
            button.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            button.setTextColor(Color.parseColor("#1A1A1A"))
            button.strokeColor = ColorStateList.valueOf(Color.parseColor("#DDDDDD"))
            button.strokeWidth = 1
        }
    }

    private fun resetButtonVisuals() {
        val btns = listOf(binding.btnAtoZ, binding.btnZtoA, binding.btnPriceLowHigh, binding.btnPriceHighLow)
        btns.forEach { setButtonStyle(it as MaterialButton, false) }
    }

    private fun resetUI() {
        binding.rangeSlider.values = listOf(binding.rangeSlider.valueFrom, binding.rangeSlider.valueTo)
        selectedNameSort = null
        selectedPriceSort = null
        resetButtonVisuals()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}