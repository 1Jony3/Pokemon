package com.example.pokemon.model.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.model.data.api.PokemonAPI
import com.example.pokemon.model.data.entities.NamedApiResource
import com.example.pokemon.model.utils.Constants
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRemotePagingSource @Inject constructor(
    val pokemonAPI: PokemonAPI
) : PagingSource<Int, NamedApiResource>()
{
    override fun getRefreshKey(state: PagingState<Int, NamedApiResource>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NamedApiResource> {
        val position = params.key ?: 0
        return try {
            val pokemon = pokemonAPI.getPokemonList(offset = position.toString())
            val nextKey = if (pokemon.results.isEmpty()) {
                null
            } else {
                position + Constants.PAGE_SIZE
            }
            LoadResult.Page(
                data = pokemon.results,
                prevKey = if (position == 0) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
}