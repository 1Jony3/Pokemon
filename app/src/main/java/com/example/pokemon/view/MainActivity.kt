package com.example.pokemon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.model.data.NamedApiResource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.main, PokeList()).commitAllowingStateLoss()
    }
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main, fragment).addToBackStack("fDetails").commitAllowingStateLoss()
    }
    fun showDetails(poke: NamedApiResource) {
        val bundle = Bundle()
        bundle.putString("keyURLPokemon", poke.name)

        val detailsFragment = PokeDetails()
        detailsFragment.arguments = bundle

        showFragment(detailsFragment)
    }
}