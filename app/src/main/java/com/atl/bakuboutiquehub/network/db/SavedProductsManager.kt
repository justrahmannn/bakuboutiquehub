package com.atl.bakuboutiquehub.network.db

import com.atl.bakuboutiquehub.homescreens.home.Product

object SavedProductsManager {

    private val savedProducts = mutableSetOf<Product>()

    fun toggleProduct(product: Product): Boolean {
        return if (savedProducts.contains(product)) {
            savedProducts.remove(product)
            false // Silindi
        } else {
            savedProducts.add(product)
            true // Əlavə edildi
        }
    }

    fun isSaved(product: Product): Boolean {
        return savedProducts.contains(product)
    }

    fun getSavedProducts(): List<Product> {
        return savedProducts.toList()
    }
}