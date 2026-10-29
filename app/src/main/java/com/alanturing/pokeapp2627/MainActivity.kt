package com.alanturing.pokeapp2627

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alanturing.pokeapp2627.features.pokemon.ui.PokemonDetailRoute
import com.alanturing.pokeapp2627.features.pokemon.ui.PokemonDetailScreen
import com.alanturing.pokeapp2627.features.pokemon.ui.PokemonListRoute
import com.alanturing.pokeapp2627.features.pokemon.ui.PokemonListScreen
import com.alanturing.pokeapp2627.ui.theme.PokeApp2627Theme
import dagger.hilt.android.AndroidEntryPoint

// Única activity de la app; solo monta la navegación entre las dos pantallas
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeApp2627Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Cada ruta se enlaza con su pantalla
                    NavHost(
                        navController = navController,
                        startDestination = PokemonListRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<PokemonListRoute> {
                            PokemonListScreen(
                                // Al pulsar una tarjeta se navega al detalle pasando el id
                                onPokemonClick = { id ->
                                    navController.navigate(PokemonDetailRoute(id))
                                }
                            )
                        }
                        composable<PokemonDetailRoute> {
                            PokemonDetailScreen()
                        }
                    }
                }
            }
        }
    }
}