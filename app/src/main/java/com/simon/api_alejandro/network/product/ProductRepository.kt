package com.simon.api_alejandro.network.product

import com.simon.api_alejandro.network.product.model.ProductListResponse

class ProductRepository {
    val api = ProductService()

    suspend fun getAllProducts(): ProductListResponse {
        return api.getAllProducts()
    }
}