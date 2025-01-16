package com.simon.api_alejandro.network.product

import com.simon.api_alejandro.network.RetrofitHelper
import com.simon.api_alejandro.network.product.model.ProductListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class ProductService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllProducts(): ProductListResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ProductCLient::class.java).getAllProducts()
            return@withContext response.body()!!
        }
    }

}