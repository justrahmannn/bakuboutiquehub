package com.atl.bakuboutiquehub.network.api

import com.atl.bakuboutiquehub.network.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/api/auth/register")
    suspend fun register(@Body request: SignupRequest): Response<MessageResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}
