package com.example.pokemon.model.data.di

import com.example.pokemon.model.data.api.Repository
import com.example.pokemon.model.data.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {
    @Binds
    abstract fun bindRepository(
        repository: PokemonRepository
    ): Repository
}