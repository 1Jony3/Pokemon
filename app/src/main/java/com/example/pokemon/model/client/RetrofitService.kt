package com.example.pokemon.model.client

import com.example.pokemon.model.data.Poke
import com.example.pokemon.model.data.Pokemon
import com.example.pokemon.model.data.PokemonStat
import com.example.pokemon.model.data.PokemonType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("pokemon/")
    fun getPokemonList(@Query("offset") offset: Int,
                       @Query("limit") limit: Int): Call<Pokemon>

    @GET("pokemon/{dexNumOrName}/")
    fun getPokemon(@Path("dexNumOrName") dexNumOrName: String): Call<Poke>

    companion object {
        var retrofitService: RetrofitService? = null
        private const val baseUrl = "https://pokeapi.co/api/v2/"

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}