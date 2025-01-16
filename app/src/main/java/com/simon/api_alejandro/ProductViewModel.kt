package com.simon.api_alejandro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simon.api_alejandro.network.product.ProductRepository
import com.simon.api_alejandro.network.product.model.ProductListResponse
import com.simon.api_alejandro.network.product.model.ProductResponse

class ProductViewModel {
    private val productListRepository = ProductRepository()

    private val  _productList = MutableLiveData<List<ProductResponse>>(emptyList())
    val productList : LiveData<List<ProductResponse>> = _productList

    private  val _isLoading = MutableLiveData<Boolean>()
    val  isLoading: LiveData<Boolean> = _isLoading

    fun getAllProducts(){
        viewModelScope.launch{
            _isLoading.value = true
            _productList.postValue(ProductListResponse.getAllProducts().productList)
            _isLoading.value
        }

    }
}
