package com.example.pokemon.screen

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentListPokemonBinding
import com.example.pokemon.model.adapter.DefaultLoadStateAdapter
import com.example.pokemon.model.adapter.OnClickPokemonListener
import com.example.pokemon.model.adapter.PokemonAdapter
import com.example.pokemon.model.adapter.TryAgainAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPokemonFragment : Fragment(R.layout.fragment_list_pokemon) {


    private val viewModel by viewModels<ListPokemonViewModel>()
    private lateinit var adapter: PokemonAdapter
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder
    private lateinit var binding: FragmentListPokemonBinding

    companion object{
        const val ARG_NAME = "name"
        const val ARG_ID = "id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListPokemonBinding.bind(view)
        setupAdapter(view)
        //setupSearchInput()
        setupSwipeToRefresh()
    }

    private fun setupAdapter(view: View) {

        adapter = PokemonAdapter(object : OnClickPokemonListener {
            override fun onClick(name: String, id: Int) {
                openDetails(name, id)
            }
        })

        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.list.apply {
            adapter = adapterWithLoadState
            layoutManager = GridLayoutManager(view.context, 2)//LinearLayoutManager(view.context)
            setHasFixedSize(true)
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction
        )
        observeLoadState()
        observePokemon()
    }

    private fun observeLoadState() {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun observePokemon() {
        lifecycleScope.launchWhenStarted {
            viewModel.listData.collect { pagingData ->
                if (pagingData != null) {
                    adapter.submitData(pagingData)
                } else {
                    // Обработка состояния загрузки или ошибки
                }
            }
        }

    }
    /*private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener {
            viewModel.setSearchBy(it.toString())
        }
    }*/
    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            //viewModel.refresh()
        }
    }
    private fun openDetails(name: String, id: Int){
        d("lol", "nn details - ${findNavController().currentDestination}")
        findNavController().navigate(
            R.id.action_listPokemonFragment_to_detailsPokemonFragment,
            bundleOf(ARG_NAME to name, ARG_ID to id))
    }

}