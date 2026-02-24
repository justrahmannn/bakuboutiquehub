package com.atl.bakuboutiquehub.network.model

data class ProductDTO(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val price: Double,
    val imageUrl: String? = null,
    val stockCount: Int? = null,
    val boutiqueId: Long? = null
)
