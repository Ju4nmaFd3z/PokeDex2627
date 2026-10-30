package com.alanturing.pokeapp2627.features.pokemon.ui

import androidx.lifecycle.ViewModel
import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import com.alanturing.pokeapp2627.features.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Solo conecta la pantalla con el repositorio; el estado lo lleva la pantalla
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val _repository: PokemonRepository
) : ViewModel() {

    // Una página de la lista, empezando por los Pokémon que ya hay cargados
    suspend fun readPage(offset: Int): List<Pokemon> {
        return _repository.readAllPokemon(offset, PAGE_SIZE)
    }

    private companion object {
        // Un valor pequeño para no sobrecargar la API
        const val PAGE_SIZE = 20
    }
}