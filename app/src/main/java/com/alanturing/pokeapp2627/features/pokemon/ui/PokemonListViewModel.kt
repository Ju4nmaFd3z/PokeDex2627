package com.alanturing.pokeapp2627.features.pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import com.alanturing.pokeapp2627.features.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

// Todo lo que la pantalla de lista necesita para dibujarse
data class PokemonListUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
)

// Pide los Pokémon al repositorio y guarda el estado de la pantalla
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val _repository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    // La pantalla solo puede leer el estado, no modificarlo
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        loadNextPage()
    }

    // Carga la siguiente página; si ya hay una en curso o no quedan más, no hace nada
    fun loadNextPage() {
        val current = _uiState.value
        if (current.isLoading || current.endReached) return

        // Se marca antes de lanzar la corrutina para que no se cuelen llamadas repetidas
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                // El offset es cuántos Pokémon hay ya cargados
                val page = _repository.readAllPokemon(
                    offset = current.pokemons.size,
                    limit = PAGE_SIZE
                )
                _uiState.update {
                    it.copy(
                        pokemons = it.pokemons + page,
                        isLoading = false,
                        // Si llegan menos de los pedidos, era la última página
                        endReached = page.size < PAGE_SIZE
                    )
                }
            } catch (e: IOException) {
                // Sin red o error de la API: la pantalla ofrece reintentar
                _uiState.update {
                    it.copy(isLoading = false, error = "No se han podido cargar los Pokémon")
                }
            }
        }
    }

    private companion object {
        // Un valor pequeño para no sobrecargar la API
        const val PAGE_SIZE = 20
    }
}
