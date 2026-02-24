package com.atl.bakuboutiquehub.network.api

import com.atl.bakuboutiquehub.network.model.BoutiqueDTO
import com.atl.bakuboutiquehub.network.model.ProductDTO
import retrofit2.Response
import retrofit2.http.*

interface BoutiqueApiService {

    @POST("/api/boutiques")
    suspend fun createBoutique(@Body boutique: BoutiqueDTO): Response<BoutiqueDTO>

    @GET("/api/boutiques")
    suspend fun getAllBoutiques(): Response<List<BoutiqueDTO>>

    @POST("/api/boutiques/{boutiqueId}/products")
    suspend fun addProduct(
        @Path("boutiqueId") boutiqueId: Long,
        @Body product: ProductDTO
    ): Response<ProductDTO>

    @GET("/api/boutiques/{boutiqueId}/products")
    suspend fun getProducts(@Path("boutiqueId") boutiqueId: Long): Response<List<ProductDTO>>
}
