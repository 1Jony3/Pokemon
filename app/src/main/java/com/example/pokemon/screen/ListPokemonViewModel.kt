package com.example.pokemon.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokemon.model.data.api.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemon.model.data.entities.NamedApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class ListPokemonViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _listData = MutableStateFlow<PagingData<NamedApiResource>?>(null)
    val listData: StateFlow<PagingData<NamedApiResource>?> = _listData.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                repository.getPokemonList()
                    .cachedIn(viewModelScope)
                    .collect { pagingData ->
                        _listData.value = pagingData
                    }
            } catch (e: Exception) {
                Log.e("lol", e.toString())
            }
        }
    }
}
    /*private fun getProducts() = searchBy.asFlow()
        .debounce(500)
        .flatMapLatest {
            repository.getPokemonList()
        }
        .cachedIn(viewModelScope)
/*
    /*fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.postValue(value)
        listData = getProducts()
    }
    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)
    }*/