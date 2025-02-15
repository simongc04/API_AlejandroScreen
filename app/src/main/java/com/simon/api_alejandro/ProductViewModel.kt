package com.simon.api_alejandro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.simon.api_alejandro.db.ProductDatabase
import com.simon.api_alejandro.network.product.ProductRepository
import com.simon.api_alejandro.network.product.model.ProductListResponse
import com.simon.api_alejandro.network.product.model.ProductResponse
import com.simon.api_alejandro.db.Product
import com.simon.api_alejandro.db.productResponseToProduct
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao = ProductDatabase.getDatabase(application).dao
    private val productRepository = ProductRepository()

    private val _productList = MutableLiveData<List<ProductResponse>>(emptyList())
    val productList: LiveData<List<ProductResponse>> = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favoriteProductList = productDao.getFavoriteProductList().asLiveData()
    val favoriteProductList: LiveData<List<Product>> = _favoriteProductList

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _productList.postValue(productRepository.getAllProducts().productList)
            _isLoading.value = false
        }
    }

    fun addToFavorites(product: ProductResponse) {
        viewModelScope.launch {
            productDao.upsertProduct(productResponseToProduct(product))
        }
    }

    fun removeFromFavorites(productId: Int) {
        viewModelScope.launch {
            productDao.deleteFavoriteProduct(productId)
        }
    }
}
