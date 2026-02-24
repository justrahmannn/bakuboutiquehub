package com.atl.bakuboutiquehub.network.model

data class BoutiqueDTO(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val address: String? = null,
    val contactNumber: String? = null,
    val workingHours: String? = null,
    val categories: List<String>? = null,
    val brands: List<String>? = null,
    val ownerId: Long? = null
)
