package com.alanturing.pokeapp2627.features.pokemon.data.remote

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon

interface PokemonDataSource {
    suspend fun readAllPokemon():List<Pokemon>
    suspend fun readPokemonByName(name:String): Pokemon?
    suspend fun readPokemonById(id:Int): Pokemon?
}