package com.alanturing.pokeapp2627.features.pokemon.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface PokeApi {

    @GET("/pokemon")
    suspend fun readAllPokemon(): Response<PokemonListResult>
    @GET("/pokemon/{name}")
    suspend fun readPokemonByName(@Path("name") name:String): Response<PokemonResult>
    @GET("/pokemon/{id}")
    suspend fun readPokemonById(@Path("id") id:Int): Response<PokemonResult>
}

