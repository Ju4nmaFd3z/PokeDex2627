package com.alanturing.pokeapp2627.features.pokemon.data.repository

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon

interface PokemonRepository {

    suspend fun readAllPokemon():List<Pokemon>
    suspend fun readPokemonById(): Pokemon
}