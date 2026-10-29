package com.alanturing.pokeapp2627.features.pokemon.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import com.alanturing.pokeapp2627.features.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

// Todo lo que la pantalla de detalle necesita para dibujarse
data class PokemonDetailUiState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)

// Busca un Pokémon por id y guarda el estado de la pantalla
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val _repository: PokemonRepository
) : ViewModel() {

    // El id llega en la ruta; toRoute lo recupera de SavedStateHandle
    private val _id: Int = savedStateHandle.toRoute<PokemonDetailRoute>().id

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        _uiState.value = PokemonDetailUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val pokemon = _repository.readPokemonById(_id)
                _uiState.value = if (pokemon != null) {
                    PokemonDetailUiState(pokemon = pokemon, isLoading = false)
                } else {
                    // La API respondió 404: ese id no existe
                    PokemonDetailUiState(isLoading = false, error = "No se ha encontrado el Pokémon")
                }
            } catch (e: IOException) {
                _uiState.value = PokemonDetailUiState(
                    isLoading = false,
                    error = "No se ha podido cargar el Pokémon"
                )
            }
        }
    }
}
