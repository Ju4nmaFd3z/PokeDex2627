package com.alanturing.pokeapp2627.features.pokemon.data.remote

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon

data class PokemonResult(
    val id:Int,
    val name: String,
    val sprites: PokemonSprites
)
data class PokemonSprites(
    val frontDefault:String,
)
data class PokemonListResult(
    val results: List<PokemonListItemResult>
)
data class PokemonListItemResult(
    val name:String,
    val url: String,
)

fun PokemonResult.toModel(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        pokemonImageUrl = this.sprites.frontDefault,
    )

}