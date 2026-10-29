package com.alanturing.pokeapp2627.features.pokemon.di

import com.alanturing.pokeapp2627.features.pokemon.data.remote.PokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Construye Retrofit y entrega una única instancia de PokeApi
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): PokeApi {
        val retrofit:Retrofit = Retrofit.Builder()
            // La base url debe acabar en "/", si no Retrofit da error
            .baseUrl("https://pokeapi.co/api/v2/")
            // Gson convierte el JSON de la respuesta en nuestras data class
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PokeApi::class.java)
    }
}