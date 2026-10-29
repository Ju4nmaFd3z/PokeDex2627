package com.alanturing.pokeapp2627.features.pokemon.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

// Cada función es una petición HTTP a PokéAPI
interface PokeApi {

    // Lista paginada: offset indica por dónde empezar y limit cuántos pide
    // Sin "/" al principio, para que respete el /api/v2/ de la base url
    @GET("pokemon")
    suspend fun readAllPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonListResult>
    @GET("pokemon/{name}")
    suspend fun readPokemonByName(@Path("name") name:String): Response<PokemonResult>
    @GET("pokemon/{id}")
    suspend fun readPokemonById(@Path("id") id:Int): Response<PokemonResult>
}
