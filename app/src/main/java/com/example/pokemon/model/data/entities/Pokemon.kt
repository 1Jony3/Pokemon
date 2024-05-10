package com.example.pokemon.model.data.entities

data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val stats: List<Stat>,
    val types: List<Type>,
    val sprites: Sprites
    )