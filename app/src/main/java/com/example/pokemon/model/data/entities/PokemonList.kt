package com.example.pokemon.model.data.entities

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<NamedApiResource>)