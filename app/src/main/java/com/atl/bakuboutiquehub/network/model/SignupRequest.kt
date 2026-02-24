package com.atl.bakuboutiquehub.network.model

data class SignupRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val gender: String? = null,
    val age: Int? = null,
    val referralSource: String? = null,
    val roles: Set<String>? = null
)