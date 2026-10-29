package com.alanturing.pokeapp2627.features.pokemon.data.remote

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon

// Ahora solo hay una fuente remota, pero podría haber otra local
interface PokemonDataSource {
    // Una página de la lista; lanza IOException si falla la red o la API
    suspend fun readAllPokemon(offset: Int, limit: Int): List<Pokemon>
    // Las búsquedas devuelven null si el Pokémon no existe
    suspend fun readPokemonByName(name:String): Pokemon?
    suspend fun readPokemonById(id:Int): Pokemon?
}