package com.alanturing.pokeapp2627.features.pokemon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage

// Muestra la imagen, el nombre y el id del Pokémon; el id lo saca el ViewModel de la ruta
@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    // Variable local para que Kotlin sepa que no es null dentro del when
    val pokemon = state.pokemon

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tres casos: cargando, con datos o con error
        when {
            state.isLoading -> CircularProgressIndicator()
            pokemon != null -> {
                AsyncImage(
                    model = pokemon.pokemonImageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(text = "#${pokemon.id}")
            }
            else -> {
                Text(text = state.error ?: "Algo ha fallado")
                Button(onClick = { viewModel.loadPokemon() }) {
                    Text("Reintentar")
                }
            }
        }
    }
}
