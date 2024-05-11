package com.example.pokemon.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentDetailsPokemonBinding
import com.example.pokemon.model.adapter.DetailsPokemonHolder
import com.example.pokemon.model.utils.Constants.ARG_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsPokemonFragment : Fragment(R.layout.fragment_details_pokemon) {

    private lateinit var binding: FragmentDetailsPokemonBinding
    private val viewModel by viewModels<DetailsPokemonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPokemonBinding.inflate(inflater, container, false)
        val idPokemon = requireArguments().getInt(ARG_ID)
        viewModel.getPokemon(idPokemon)
        val holder = DetailsPokemonHolder(binding)

        viewModel.state.observe(viewLifecycleOwner){
            binding.progressBar.visibility =
                if (it == DetailsPokemonViewModel.State.Loading) View.VISIBLE else View.GONE

            binding.container.visibility =
                if (it is DetailsPokemonViewModel.State.Loaded) View.VISIBLE else View.GONE

            if (it is DetailsPokemonViewModel.State.Error)
                Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
            if (it is DetailsPokemonViewModel.State.Loaded) {
                holder.bind(it.product)
            }
        }
        return binding.root
    }


}