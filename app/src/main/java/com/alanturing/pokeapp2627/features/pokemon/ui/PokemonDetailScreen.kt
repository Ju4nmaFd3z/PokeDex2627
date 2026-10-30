package com.alanturing.pokeapp2627.features.pokemon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import java.io.IOException

// Los estados viven en la pantalla; el ViewModel solo pide el Pokémon
@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    var pokemon by remember { mutableStateOf<Pokemon?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    // Al subirlo se vuelve a lanzar el efecto; así funciona el botón de reintentar
    var attempt by remember { mutableIntStateOf(0) }

    LaunchedEffect(attempt) {
        isLoading = true
        error = null
        try {
            pokemon = viewModel.readPokemon()
            if (pokemon == null) error = "No se ha encontrado el Pokémon"
        } catch (e: IOException) {
            error = "No se ha podido cargar el Pokémon"
        }
        isLoading = false
    }

    // Variable local para que Kotlin sepa que no es null dentro del when
    val current = pokemon

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            current != null -> {
                AsyncImage(
                    model = current.pokemonImageUrl,
                    contentDescription = current.name,
                    modifier = Modifier.size(200.dp)
                )
                Text(text = current.name.replaceFirstChar { it.uppercase() })
                Text(text = "#${current.id}")
            }
            else -> {
                Text(text = error ?: "Algo ha fallado")
                Button(onClick = { attempt++ }) {
                    Text("Reintentar")
                }
            }
        }
    }
}