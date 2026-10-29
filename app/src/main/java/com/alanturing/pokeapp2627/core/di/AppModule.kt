package com.alanturing.pokeapp2627.core.di

import com.alanturing.pokeapp2627.features.pokemon.data.remote.PokemonDataSource
import com.alanturing.pokeapp2627.features.pokemon.data.remote.PokemonRemoteDataSource
import com.alanturing.pokeapp2627.features.pokemon.data.repository.PokemonRepository
import com.alanturing.pokeapp2627.features.pokemon.data.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Le dice a Hilt qué clase concreta usar para cada interfaz
@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    // Quien pida un PokemonDataSource recibe el remoto, el que usa la API
    @Binds
    @Singleton
    fun bindsPokemonRemoteDataSource(ds: PokemonRemoteDataSource): PokemonDataSource

    // Quien pida un PokemonRepository recibe PokemonRepositoryImpl
    @Binds
    @Singleton
    fun bindsPokemonRepository(repo: PokemonRepositoryImpl): PokemonRepository
}