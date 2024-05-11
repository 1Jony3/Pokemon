package com.example.pokemon.model.data.repository

import android.util.Log.d
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.model.data.api.PokemonAPI
import com.example.pokemon.model.data.entities.NamedApiResource
import com.example.pokemon.model.utils.Constants
import com.example.pokemon.model.utils.Constants.SEARCH_LOAD_SIZE
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRemotePagingSource @Inject constructor(
    private val pokemonAPI: PokemonAPI,
    private val searchBy: String
) : PagingSource<Int, NamedApiResource>() {
    override fun getRefreshKey(state: PagingState<Int, NamedApiResource>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NamedApiResource> {
        val position = params.key ?: 0

        val loadSize = if (searchBy == null) params.loadSize else SEARCH_LOAD_SIZE

        return try {
            val pokemon = pokemonAPI.getPokemonList(offset = position.toString(), limit = loadSize.toString())

            val filteredPokemon = if (searchBy.replace(" ", "") != "") {
                pokemon.results.filter { it.name.contains(searchBy, true) }
            } else {
                pokemon.results
            }
            d("lol", "$filteredPokemon")

            val nextKey = if (filteredPokemon.isEmpty()) {
                null
            } else {
                position + Constants.PAGE_SIZE
            }
            LoadResult.Page(
                data = filteredPokemon,
                prevKey = if (position == 0) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
}