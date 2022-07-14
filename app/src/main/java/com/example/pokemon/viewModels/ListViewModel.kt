package com.example.pokemon.viewModels

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.SingleLiveEvent
import com.example.pokemon.model.client.Repository
import com.example.pokemon.model.data.*
import com.example.pokemon.view.PokeList.Companion.countElement
import com.example.pokemon.view.PokeList.Companion.pagin
import com.example.pokemon.viewModels.adapter.PokeAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListViewModel constructor(private val repository: Repository): ViewModel() {
    init {
        Log.e("aaaaaa", "create list")
    }

    val pokemonList = SingleLiveEvent<List<NamedApiResource>>()
    val errorMessage = SingleLiveEvent<String>()
    private val statsList: MutableList<Stats> = mutableListOf()
    private var adapter: PokeAdapter? = null
    private var indexSelectedElement = 0
    var choise: Int = 0
    var chip: MutableList<Boolean> = mutableListOf(false, false, false)


    fun getUploadData(): SingleLiveEvent<List<NamedApiResource>> {
        return pokemonList
    }

    fun setAdapter(list: List<NamedApiResource>) {
        adapter = PokeAdapter(list, false)
        Log.e("aaaaaa", "$adapter")
        /*adapter!!.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY*/
    }

    fun changeAdapter(list: List<NamedApiResource>){
        adapter!!.setPokemon(list)
        adapter!!.notifyDataSetChanged()
    }

    fun getAdapter(): PokeAdapter? {
        return adapter
    }

    private fun setPokemonList(list: List<NamedApiResource>){
        pokemonList.value = list
    }

    fun mix() {
        setPokemonList(pokemonList.value!!.shuffled())
    }

    fun getPokeList(offset: Int, limit: Int, progressBar: ProgressBar) {
        val response = repository.getPokemonList(offset, limit)
        response.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                pokemonList.postValue(response.body()!!.results)
                getInfoStats(response.body()!!.results)
            }
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
        progressBar.visibility = View.GONE
    }

    private fun getPoke(name: String) {
        val response = repository.getPokemon(name)
        response.enqueue(object : Callback<Poke> {
            override fun onResponse(call: Call<Poke>, response: Response<Poke>) {
                setStatsList(response.body()!!.stats, name)
            }

            override fun onFailure(call: Call<Poke>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("errorM", "poke dont get")
            }
        })
    }
    private fun getInfoStats(list: List<NamedApiResource>) {
        for (i in (countElement - pagin) until countElement) {
            getPoke(list[i].name)
        }
    }
    private fun setStatsList(list: List<PokemonStat>, name: String) {
        val stat = Stats(
            name,
            list[0].baseStat,
            list[1].baseStat,
            list[2].baseStat
        )
        statsList.add(stat)
    }
    fun findHpAttackDefense() {
        var max = 0
        var element = statsList[0]
        statsList.forEach {
            if ((it.hp + it.defense + it.attack) > max){
                max = it.hp + it.defense + it.attack
                element = it
            }
        }
        findElement(element.name)
        Log.e("aaaa", "$element...$max")
    }
    fun findHpAttack() {
        var max = 0
        var element = statsList[0]
        statsList.forEach {
            if ((it.hp + it.attack) > max){
                max = it.hp + it.attack
                element = it
            }
        }
        findElement(element.name)
        Log.e("aaaa", "$element...$max")
    }
    fun findHpDefense() {
        var max = 0
        var element = statsList[0]
        statsList.forEach {
            if ((it.hp + it.defense) > max){
                max = it.hp + it.defense
                element = it
            }
        }
        findElement(element.name)
        Log.e("aaaa", "$element...$max")
    }
    fun findAttackDefense() {
        var max = 0
        var element = statsList[0]
        statsList.forEach {
            if ((it.attack + it.defense) > max){
                max = it.attack + it.defense
                element = it
            }
        }
        findElement(element.name)
        Log.e("aaaa", "$element...$max")
    }

    fun findHp() {
        findElement(statsList.maxByOrNull { it.hp }!!.name)
    }
    fun findAttack() {
        findElement(statsList.maxByOrNull { it.attack }!!.name)
    }
    fun findDefense() {
        findElement(statsList.maxByOrNull { it.defense }!!.name)
    }
    private fun fjfjf(){

    }
    private fun findElement(name: String){
        indexSelectedElement = pokemonList.value!!.indexOf(pokemonList.value!!.find { it.name == name })
        colorItem()
    }
    private fun colorItem(){
        val tempList = pokemonList.value!!.toMutableList()
        val element = tempList[indexSelectedElement]
        tempList.removeAt(indexSelectedElement)
        tempList.add(element)
        adapter!!.setSelectedPosition(true)
        setPokemonList(tempList)
        tempList.clear()
    }
}
