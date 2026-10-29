package com.alanturing.pokeapp2627.features.pokemon.data.repository

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import com.alanturing.pokeapp2627.features.pokemon.data.remote.PokemonDataSource
import javax.inject.Inject

// De momento solo delega en el datasource remoto
class PokemonRepositoryImpl @Inject constructor(
    private val _remoteDataSource: PokemonDataSource
): PokemonRepository {
    override suspend fun readAllPokemon(offset: Int, limit: Int): List<Pokemon> {
        return _remoteDataSource.readAllPokemon(offset, limit)
    }

    override suspend fun readPokemonById(id: Int): Pokemon? {
        return _remoteDataSource.readPokemonById(id)
    }
}