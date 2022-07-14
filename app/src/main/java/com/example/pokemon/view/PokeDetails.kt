package com.example.pokemon.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.R
import com.example.pokemon.model.client.Repository
import com.example.pokemon.model.client.RetrofitService
import com.example.pokemon.viewModels.DetailsViewModel
import com.example.pokemon.viewModels.DetailsViewModelFactory
import com.example.pokemon.viewModels.adapter.DetailsHolder

class PokeDetails : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var namePokemon: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        namePokemon = arguments!!.getString("keyURLPokemon") as String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_poke_details, container, false)
        viewModel = ViewModelProvider(this, DetailsViewModelFactory(Repository(retrofitService))).get(
            DetailsViewModel::class.java)
        val holder = DetailsHolder(view)
        viewModel.pokemon.observe(this, {
            holder.bind(it)
        })

        viewModel.errorMessage.observe(this, {
        })
        viewModel.getPoke(namePokemon)

        return view
    }

}