package com.simon.api_alejandro.network.product

import android.util.Log
import com.simon.api_alejandro.network.product.model.ProductListResponse
import retrofit2.Response

class ProductRepository {
    val api = ProductService()

    suspend fun getAllProducts(): ProductListResponse {
        val response = api.getAllProducts()
        if (response.isSuccessful) {
            Log.d("ProductRepository", "API call successful")
            return response.body() ?: ProductListResponse(emptyList(), 0, 0, 0)
        } else {
            Log.e("ProductRepository", "API call failed: ${response.errorBody()?.string()}")
            throw Exception("Failed to fetch products")
        }
    }
}
