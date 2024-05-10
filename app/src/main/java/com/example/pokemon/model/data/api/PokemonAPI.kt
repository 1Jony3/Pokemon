package com.example.pokemon.model.data.api

import com.example.pokemon.model.data.entities.Pokemon
import com.example.pokemon.model.data.entities.PokemonList
import com.example.pokemon.model.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: String,
        @Query("limit") limit: String? = Constants.PAGE_SIZE.toString())
    : PokemonList

    @GET("pokemon/{dexNumOrName}/")
    suspend fun getPokemon(@Path("dexNumOrName") dexNumOrName: String): Pokemon
}