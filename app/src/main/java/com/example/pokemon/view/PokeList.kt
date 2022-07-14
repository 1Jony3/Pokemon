package com.example.pokemon.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.model.client.Repository
import com.example.pokemon.model.client.RetrofitService
import com.example.pokemon.viewModels.ListViewModel
import com.example.pokemon.viewModels.ListViewModelFactory
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PokeList : Fragment() {

    companion object {
        const val pagin = 30
        private const val startElement = 0
        var countElement = 30
    }


    private lateinit var viewModel: ListViewModel
    private val retrofitService = RetrofitService.getInstance()

    private var recyclerView: RecyclerView? = null
    private lateinit var button: FloatingActionButton
    private lateinit var chipHp: Chip
    private lateinit var chipAttack: Chip
    private lateinit var chipDefense: Chip

    lateinit var progressAddList: ProgressBar
    lateinit var progressList: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ListViewModelFactory(Repository(retrofitService))).get(
            ListViewModel::class.java)
        viewModel.getUploadData().observe(this, Observer {
            if(viewModel.getAdapter() == null)
                viewModel.setAdapter(it)
            else viewModel.changeAdapter(it)
            recyclerView!!.adapter = viewModel.getAdapter()

        })
        viewModel.errorMessage.observe(this, {
        })
        Log.e("aaaaa", "create view")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_poke_list, container, false)
        recyclerView = view!!.findViewById(R.id.poke_list)
        button = view.findViewById(R.id.floating_action_button)
        chipHp = view.findViewById(R.id.hpC)
        chipAttack = view.findViewById(R.id.attackC)
        chipDefense = view.findViewById(R.id.defenseC)
        progressAddList = view.findViewById(R.id.progressAdd)
        progressList = view.findViewById(R.id.progressList)

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView!!.layoutManager = layoutManager
        setList()

        button.setOnClickListener {
            viewModel.mix()
        }
        chipHp.setOnClickListener{
            if (chipHp.chipBackgroundColor == ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.transparentBackgroundYellow))) {
                chipHp.setChipBackgroundColorResource(R.color.transparentBackgroundGray)
                viewModel.choise--
                viewModel.chip[0] = false
            }
            else {
                chipHp.setChipBackgroundColorResource(R.color.transparentBackgroundYellow)
                viewModel.choise++
                viewModel.chip[0] = true
                if (viewModel.choise == 1) viewModel.findHp()
                else if (viewModel.choise == 3) viewModel.findHpAttackDefense()
                else if (viewModel.chip[1] == true) viewModel.findHpAttack()
                else viewModel.findHpDefense()
            }
        }
        chipDefense.setOnClickListener{
            if (chipAttack.chipBackgroundColor == ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.transparentBackgroundYellow))) {
                chipAttack.setChipBackgroundColorResource(R.color.transparentBackgroundGray)
                viewModel.choise--
                viewModel.chip[2] = false
            }
            else {
                chipAttack.setChipBackgroundColorResource(R.color.transparentBackgroundYellow)
                viewModel.choise++
                viewModel.chip[2] = true
                if (viewModel.choise == 1) viewModel.findDefense()
                else if (viewModel.choise == 3) viewModel.findHpAttackDefense()
                else if (viewModel.chip[0] == true) viewModel.findHpDefense()
                else viewModel.findAttackDefense()
            }
        }
        chipAttack.setOnClickListener{
            if (chipAttack.chipBackgroundColor == ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.transparentBackgroundYellow))) {
                chipAttack.setChipBackgroundColorResource(R.color.transparentBackgroundGray)
                viewModel.choise--
                viewModel.chip[1] = false
            }
            else {
                chipAttack.setChipBackgroundColorResource(R.color.transparentBackgroundYellow)
                viewModel.choise++
                viewModel.chip[1] = true
                if (viewModel.choise == 1) viewModel.findAttack()
                else if (viewModel.choise == 3) viewModel.findHpAttackDefense()
                else if (viewModel.chip[0] == true) viewModel.findHpAttack()
                else viewModel.findAttackDefense()
            }
        }

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    progressAddList.visibility = View.VISIBLE
                    countElement += pagin
                    viewModel.getPokeList(startElement, countElement, progressAddList)
                }
            }
        })
        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("keyFragmentList", true)
    }

    private fun setList(){
        progressList.visibility = View.VISIBLE

        viewModel.getPokeList(startElement, countElement, progressList)
    }

}