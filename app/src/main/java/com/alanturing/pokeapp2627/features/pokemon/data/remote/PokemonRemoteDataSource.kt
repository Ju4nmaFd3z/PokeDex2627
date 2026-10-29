package com.alanturing.pokeapp2627.features.pokemon.data.remote

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

// Pide los datos a PokéAPI con Retrofit y los convierte a Pokemon
class PokemonRemoteDataSource
@Inject constructor(
    private val _api:PokeApi
): PokemonDataSource {

    // Una página de la lista = una llamada
    override suspend fun readAllPokemon(offset: Int, limit: Int): List<Pokemon> {
        val response = _api.readAllPokemon(offset, limit)
        // Un error HTTP no lanza excepción por sí solo, así que lo convierto en una
        if (!response.isSuccessful) {
            throw IOException("Error ${response.code()} al leer la lista")
        }
        val body = response.body() ?: throw IOException("Respuesta vacía")
        return body.results.map { it.toModel() }
    }

    override suspend fun readPokemonByName(name: String): Pokemon? {
        return _api.readPokemonByName(name).toPokemonOrNull()
    }

    override suspend fun readPokemonById(id: Int): Pokemon? {
        return _api.readPokemonById(id).toPokemonOrNull()
    }

    // 404 -> null (no existe); cualquier otro fallo -> excepción
    private fun Response<PokemonResult>.toPokemonOrNull(): Pokemon? {
        if (isSuccessful) return body()?.toModel()
        if (code() == 404) return null
        throw IOException("Error ${code()} al leer el Pokémon")
    }

}