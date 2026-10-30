package com.alanturing.pokeapp2627.features.pokemon.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import com.alanturing.pokeapp2627.features.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Solo conecta la pantalla con el repositorio; el estado lo lleva la pantalla
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val _repository: PokemonRepository
) : ViewModel() {

    // El id llega en la ruta; toRoute lo recupera de SavedStateHandle
    private val _id: Int = savedStateHandle.toRoute<PokemonDetailRoute>().id

    // Devuelve null si el Pokémon no existe
    suspend fun readPokemon(): Pokemon? {
        return _repository.readPokemonById(_id)
    }
}