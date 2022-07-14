package com.example.pokemon.viewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.model.client.Repository
import com.example.pokemon.model.data.Poke
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel constructor(private val repository: Repository): ViewModel() {
    val pokemon = MutableLiveData<Poke>()
    val errorMessage = MutableLiveData<String>()

    fun getPoke(name: String) {

        val response = repository.getPokemon(name)
        response.enqueue(object : Callback<Poke> {
            override fun onResponse(call: Call<Poke>, response: Response<Poke>) {
                Log.d("keyt", "${name} ... ${response.body()}")
                pokemon.postValue(response.body())
            }

            override fun onFailure(call: Call<Poke>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }


}