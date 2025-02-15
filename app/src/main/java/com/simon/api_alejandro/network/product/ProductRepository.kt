package com.simon.api_alejandro.network.product;

import com.simon.api_alejandro.network.product.model.ProductListResponse;

class ProductRepository {
    private val api = ProductService();

    suspend fun getAllProducts(): ProductListResponse {
        val response = api.getAllProducts()
        if (response.isSuccessful) {
            return response.body() ?: ProductListResponse(emptyList(), 0, 0, 0)
        } else {
            throw Exception("Failed to fetch products")
        }
    }
}