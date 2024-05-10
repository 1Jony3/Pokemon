package com.example.pokemon.screen

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.data.api.Repository
import com.example.pokemon.model.data.entities.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsPokemonViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _product = MutableLiveData<Pokemon>()
    val product: LiveData<Pokemon> = _product

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Loaded(val product: Pokemon) : State
    }

    fun getPokemon(id: Int) {
        _state.postValue(State.Loading)
        viewModelScope.launch {
            flow {
                emit(repository.getPokemon(id.toString()))
            }.flowOn(Dispatchers.IO).catch { e ->
                d("lol", " error ${e.printStackTrace()}")
                _state.postValue(State.Error(e.message.toString()))
                e.printStackTrace()
            }.collect {
                d("lol", "$it")
                _product.value = it
                _state.postValue(State.Loaded(it))
            }
        }
    }
}