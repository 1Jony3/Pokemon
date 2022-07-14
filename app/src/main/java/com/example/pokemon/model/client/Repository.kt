package com.example.pokemon.model.client

class Repository constructor(private val retrofitService: RetrofitService) {

    fun getPokemonList(offset: Int, limit: Int) = retrofitService.getPokemonList(offset, limit)
    fun getPokemon(name: String) = retrofitService.getPokemon(name)
}