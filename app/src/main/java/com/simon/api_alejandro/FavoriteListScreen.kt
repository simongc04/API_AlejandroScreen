package com.simon.api_alejandro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.simon.api_alejandro.db.Product
import com.simon.api_alejandro.network.product.model.ProductResponse

@Composable
fun FavoriteListScreen(productViewModel: ProductViewModel) {
    val favoriteProductList by productViewModel.favoriteProductList.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 25.dp, bottom = 10.dp),
                text = "Lista de favoritos",
                fontSize = TextUnit(8f, TextUnitType.Em),
                fontWeight = FontWeight.Bold
            )
        }
        if (favoriteProductList.isEmpty()) {
            item {
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = "Lista vacía",
                    fontSize = TextUnit(8f, TextUnitType.Em)
                )
            }
        } else {
            items(favoriteProductList) { product ->
                ProductCard(product = productToProductResponse(product), isFavorite = true, productViewModel)
            }
        }
    }
}

fun productToProductResponse(product: Product): ProductResponse {
    return ProductResponse(
        id = product.id,
        title = product.title,
        description = product.description,
        price = product.price,
        discountPercentage = product.discountPercentage,
        rating = product.rating,
        stock = product.stock,
        brand = product.brand,
        category = product.category,
        thumbnail = product.thumbnail
    )
}
