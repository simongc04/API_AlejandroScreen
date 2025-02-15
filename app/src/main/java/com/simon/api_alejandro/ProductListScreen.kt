package com.simon.api_alejandro

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.simon.api_alejandro.db.Product
import com.simon.api_alejandro.network.product.model.ProductResponse

@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    context: Context,
    innerPaddingValues: PaddingValues
) {
    val isLoading by productViewModel.isLoading.observeAsState(initial = true)
    val productList by productViewModel.productList.observeAsState(initial = emptyList())
    val favoriteProductList by productViewModel.favoriteProductList.observeAsState(initial = emptyList())

    if (isLoading) {
        LoadingScreen()
    } else {
        CompleteProductListScreen(productList, favoriteProductList, productViewModel, innerPaddingValues, context)
    }
}

@Composable
fun CompleteProductListScreen(
    productList: List<ProductResponse>,
    favoriteProductList: List<Product>,
    productViewModel: ProductViewModel,
    innerPaddingValues: PaddingValues,
    context: Context
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 25.dp, bottom = 10.dp),
                text = "Lista de productos",
                fontSize = TextUnit(8f, TextUnitType.Em),
                fontWeight = FontWeight.Bold
            )
        }
        if (productList.isEmpty()) {
            item {
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = "Lista vacía",
                    fontSize = TextUnit(8f, TextUnitType.Em)
                )
            }
        } else {
            items(productList) { product ->
                ProductCard(product = product, isFavorite = favoriteProductList.any { it.id == product.id }, productViewModel)
            }
        }
    }
}

@Composable
fun ProductCard(product: ProductResponse, isFavorite: Boolean, productViewModel: ProductViewModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.width(270.dp).padding(bottom = 25.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.title,
                fontSize = TextUnit(5.5f, TextUnitType.Em),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).padding(end = 10.dp)
            )
            FilledIconButton(
                modifier = Modifier.size(20.dp),
                onClick = {
                    if (isFavorite) {
                        productViewModel.removeFromFavorites(product.id)
                    } else {
                        productViewModel.addToFavorites(product)
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.Add,
                    contentDescription = if (isFavorite) "Eliminar de favoritos" else "Añadir a favoritos"
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 10.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 5.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.size(160.dp),
                model = product.thumbnail,
                contentDescription = "Imagen de ${product.title}"
            )
        }
    }
}
