package com.simon.api_alejandro

import ProductViewModel
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.simon.api_alejandro.network.product.model.ProductResponse

@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    context : Context,
    innerPaddingValues: PaddingValues
){
   val isloading: Boolean by productViewModel.isLoading.observeAsState(initial = true)
    if (isloading){
        productViewModel.getAllProducts()
        LoadingScreen()
    } else {
        CompleteProductListScreen(productViewModel.productList.value!! , innerPaddingValues)

    }
}

@Composable
fun CompleteProductListScreen(
    productList : List<ProductResponse>,
    innerPaddingValues : PaddingValues
    ){
        item{
            LazyColumn (
                modifier = modifier.padding(top = 25.dp, bottom = 10.dp),
                text = "Lista de productos",
                fontSize = TextUnit(8f,TextUnitType.Em),
                fontWeight = FontWeight.Bold
            )
        }
        if (productList.isEmpty()){
            item {
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = "Lista Vacia",
                    fontSize = TextUnit(8f, TextUnitType.Em)
                )
            }
        } else {
            items(productList){ product ->
                card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.width(260.dp).padding(bottom = 25.dp)
                ){
                    text(
                        text = product.title,
                        fontSize = TextUnit(5.5f, TextUnitType.Em),
                        textAlign = TextAlign.Center
                    )
                HorizontalDivider(
                    modifier =  modifier.padding(bottom = 10.dp),
                    thickness = 1.dp
                    color = color.Gray
                ){
                    Column (
                        modifier = Modifier.fillMaxSize().padding(start = 5.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        AsyncImage(
                            modifier = Modifier.size(160.dp),
                            model = product.thumbail,
                            contentDescription = "Imagen de ${product.title}"
                        )
                    }
                }
                }

            }

        }
    }