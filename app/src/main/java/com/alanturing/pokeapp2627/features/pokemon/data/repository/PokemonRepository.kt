package com.alanturing.pokeapp2627.features.pokemon.data.repository

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon

// Es lo único que conocen los ViewModels; esconde de dónde vienen los datos
interface PokemonRepository {

    suspend fun readAllPokemon(offset: Int, limit: Int): List<Pokemon>
    suspend fun readPokemonById(id: Int): Pokemon?
}