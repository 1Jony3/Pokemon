package com.example.pokemon.model.data.repository

import androidx.paging.PagingData
import com.example.pokemon.model.data.api.PokemonAPI
import com.example.pokemon.model.data.entities.NamedApiResource
import com.example.pokemon.model.data.entities.OfficialArtwork
import com.example.pokemon.model.data.entities.Other
import com.example.pokemon.model.data.entities.Pokemon
import com.example.pokemon.model.data.entities.PokemonList
import com.example.pokemon.model.data.entities.Sprites
import com.example.pokemon.model.data.entities.Stat
import com.example.pokemon.model.data.entities.Type
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
@ExperimentalCoroutinesApi
class PokemonRepositoryTest {

    var pokemonAPI: PokemonAPI = mockk()

    var pokemonRepository = PokemonRepository(pokemonAPI)


    @Test
    fun `getPokemon returns correct data`() = runBlocking {
        val stat = Stat(1, 1, NamedApiResource("gdgsdg", "dsc"))
        val type = Type(1, NamedApiResource("gdgsdg", "dsc"))
        val expectedPokemon = Pokemon(
            id = 1,
            name = "pikachu",
            weight = 12,
            height = 12,
            stats = listOf(stat),
            types = listOf(type),
            sprites = Sprites(other = Other(official_artwork = OfficialArtwork(front_default = "url", front_shiny = "url")))
        )
        coEvery {pokemonAPI.getPokemon("pikachu")} returns expectedPokemon


        val actualPokemon = pokemonRepository.getPokemon("pikachu")


        assertEquals(expectedPokemon, actualPokemon)
    }
}