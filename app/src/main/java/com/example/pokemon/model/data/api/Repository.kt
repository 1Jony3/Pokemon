package com.example.pokemon.model.data.api

import androidx.paging.PagingData
import com.example.pokemon.model.data.entities.NamedApiResource
import com.example.pokemon.model.data.entities.Pokemon
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPokemonList(searchBy: String): Flow<PagingData<NamedApiResource>>
    suspend fun getPokemon(name: String): Pokemon
}