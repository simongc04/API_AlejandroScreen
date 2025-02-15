package com.simon.api_alejandro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.simon.api_alejandro.db.ProductDatabase
import com.simon.api_alejandro.ui.theme.API_AlejandroTheme

class MainActivity : ComponentActivity() {
    val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = ProductDatabase::class.java,
            name = "product.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            API_AlejandroTheme(dynamicColor = false) {
                val navController: NavHostController = rememberNavController()
                val productViewModel: ProductViewModel = viewModel()
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            actions = {
                                BottomBarItems(navController, productViewModel)
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "product_list_screen",
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        composable(route = "product_list_screen") {
                            ProductListScreen(productViewModel, LocalContext.current, innerPadding)
                        }
                        composable(route = "favorite_list_screen") {
                            FavoriteListScreen(productViewModel)
                        }
                        composable(route = "search_screen") {
                            SearchScreen(productViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBarItems(navController: NavController, productViewModel: ProductViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.navigate("product_list_screen") }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Home,
                contentDescription = "Lista de productos"
            )
        }
        IconButton(
            onClick = { navController.navigate("favorite_list_screen") }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Lista de favoritos"
            )
        }
        IconButton(
            onClick = { navController.navigate("search_screen") }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Search,
                contentDescription = "Búsqueda productos"
            )
        }
    }
}
