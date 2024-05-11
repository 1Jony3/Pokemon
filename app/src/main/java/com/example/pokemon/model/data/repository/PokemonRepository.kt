package com.example.pokemon.model.data.repository

import android.icu.text.StringSearch
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.model.data.api.PokemonAPI
import com.example.pokemon.model.data.api.Repository
import com.example.pokemon.model.data.entities.NamedApiResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemon: PokemonAPI): Repository {

    override suspend fun getPokemonList(searchBy: String): Flow<PagingData<NamedApiResource>> {
        return Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { PokemonRemotePagingSource(pokemon, searchBy) }
        ).flow
    }

    override suspend fun getPokemon(name: String) = pokemon.getPokemon(name)

}