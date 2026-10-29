package com.alanturing.pokeapp2627.features.pokemon.data.remote

import com.alanturing.pokeapp2627.features.pokemon.data.model.Pokemon
import com.google.gson.annotations.SerializedName

// Cada sprite se llama como el id del Pokémon (25.png)
private const val SPRITES_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

data class PokemonResult(
    val id:Int,
    val name: String,
    val sprites: PokemonSprites
)
data class PokemonSprites(
    // En el JSON el campo es front_default y puede venir null
    @SerializedName("front_default") val frontDefault: String?,
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
        pokemonImageUrl = this.sprites.frontDefault ?: "",
    )

}

// La lista solo trae nombre y url (.../pokemon/25/), así que saco el id de la url
// Con el id monto la imagen, y así una página entera es una sola llamada
fun PokemonListItemResult.toModel(): Pokemon {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return Pokemon(
        id = id,
        name = name,
        pokemonImageUrl = "$SPRITES_URL$id.png",
    )
}