package com.alanturing.pokeapp2627.features.pokemon.ui

import kotlinx.serialization.Serializable

// Rutas de navegación; son @Serializable para poder pasar datos entre pantallas
@Serializable
object PokemonListRoute

// El detalle lleva el id del Pokémon pulsado
@Serializable
data class PokemonDetailRoute(val id: Int)
