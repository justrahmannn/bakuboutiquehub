package com.atl.bakuboutiquehub.network.model

data class SignupRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val roles: Set<String>? = null
)