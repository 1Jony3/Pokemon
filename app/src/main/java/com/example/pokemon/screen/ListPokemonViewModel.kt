package com.example.pokemon.screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ListPokemonViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var listData: Flow<PagingData<NamedApiResource>>
    private val searchBy = MutableLiveData("")

    init {
        listData = getProducts()
    }

    private fun getProducts() = searchBy.asFlow()
        .debounce(500)
        .flatMapLatest {
            repository.getPokemonList(it)
        }
        .cachedIn(viewModelScope)


    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.postValue(value)
        listData = getProducts()
    }

    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)
    }
}

