package com.example.pokemon.screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
    private val searchBy = MutableLiveData("")

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

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.postValue(value)
        //getProducts()
    }

    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)
    }
}
