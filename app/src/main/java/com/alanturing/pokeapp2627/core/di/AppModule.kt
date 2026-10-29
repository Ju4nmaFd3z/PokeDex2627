package com.alanturing.pokeapp2627.core.di

import com.alanturing.pokeapp2627.features.pokemon.data.remote.PokemonDataSource
import com.alanturing.pokeapp2627.features.pokemon.data.remote.PokemonRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import jakarta.inject.Singleton

@Module
@InstallIn(Singleton::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindsPokemonRemoteDataSource(ds: PokemonRemoteDataSource): PokemonDataSource
}