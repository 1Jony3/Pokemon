package com.example.pokemon.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
data class Pokemon(
    val results: List<NamedApiResource>,
)
data class Poke(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>
): Serializable
data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: NamedApiResource
)
data class PokemonType(val slot: Int, val type: NamedApiResource)
data class NamedApiResource(val name: String, val url: String)
data class Stats(
    var name: String,
    var hp: Int,
    var attack: Int,
    var defense: Int
)