package com.simon.api_alejandro.network.product

import com.simon.api_alejandro.network.RetrofitHelper
import com.simon.api_alejandro.network.product.model.ProductListResponse
import com.simon.api_alejandro.network.product.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductClient {
    @GET("/products")
    suspend fun getAllProducts(): Response<ProductListResponse>

    @GET("/product/{id}")
    suspend fun getProductById(id: Int): Response<ProductResponse>
}

class ProductService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val api = retrofit.create(ProductClient::class.java)

    suspend fun getAllProducts(): Response<ProductListResponse> {
        return api.getAllProducts()
    }
}
