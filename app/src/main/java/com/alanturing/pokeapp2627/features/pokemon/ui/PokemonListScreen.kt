package com.alanturing.pokeapp2627.features.pokemon.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import kotlinx.coroutines.launch
import java.io.IOException

// Los estados viven en la pantalla; el ViewModel solo pide los datos
@Composable
fun PokemonListScreen(
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    var pokemons by remember { mutableStateOf(emptyList<Pokemon>()) }
    var isLoading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var endReached by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Si ya hay una carga en curso o no quedan más páginas, no hace nada
    fun loadNextPage() {
        if (isLoading || endReached) return
        isLoading = true
        hasError = false
        scope.launch {
            try {
                val page = viewModel.readPage(pokemons.size)
                pokemons = pokemons + page
                endReached = page.isEmpty()
            } catch (e: IOException) {
                hasError = true
            }
            isLoading = false
        }
    }

    // True cuando ya se ven los últimos elementos de la lista
    val nearEnd by remember {
        derivedStateOf {
            val info = listState.layoutInfo
            val lastVisible = info.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisible >= info.totalItemsCount - 5
        }
    }

    // También carga la primera página, porque con la lista vacía nearEnd ya es true
    LaunchedEffect(nearEnd, pokemons.size) {
        if (nearEnd) loadNextPage()
    }

    LazyColumn(state = listState, modifier = modifier.fillMaxSize()) {
        items(pokemons, key = { it.id }) { pokemon ->
            PokemonRow(
                pokemon = pokemon,
                onClick = { onPokemonClick(pokemon.id) }
            )
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (hasError) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No se han podido cargar los Pokémon")
                    Button(onClick = { loadNextPage() }) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}

// Fila clicable que ocupa todo el ancho, con la imagen y el nombre
@Composable
fun PokemonRow(
    pokemon: Pokemon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        // El clickable va antes del padding para que el toque cubra toda la fila
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = pokemon.pokemonImageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = pokemon.name.replaceFirstChar { it.uppercase() })
    }
}