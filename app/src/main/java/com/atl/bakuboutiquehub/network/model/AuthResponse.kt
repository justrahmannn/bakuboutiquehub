package com.atl.bakuboutiquehub.network.model

data class AuthResponse(
    val token: String,
    val type: String,
    val id: Long,
    val email: String,
    val roles: List<String>
)
