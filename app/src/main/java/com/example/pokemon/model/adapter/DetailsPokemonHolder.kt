package com.example.pokemon.model.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentDetailsPokemonBinding
import com.example.pokemon.model.data.entities.Pokemon
import com.example.pokemon.model.data.entities.Stat
import com.example.pokemon.model.data.entities.Type

class DetailsPokemonHolder(private val binding: FragmentDetailsPokemonBinding) {

    fun bind(pokemon: Pokemon) {
        with(binding){
            loadPhoto(imageView, pokemon.sprites.other.official_artwork.front_default)
            name.text = pokemon.name
            weight.text = "weight: ${pokemon.weight}"
            height.text = "height: ${pokemon.height}"
            types.text = convertList(pokemon.types)
            hp.text = statToString(pokemon.stats[0])
            attack.text = statToString(pokemon.stats[1])
            defense.text = statToString(pokemon.stats[2])
            specialAttack.text = statToString(pokemon.stats[3])
            specialDefense.text = statToString(pokemon.stats[4])
            speed.text = statToString(pokemon.stats[5])
        }
    }

    private fun loadPhoto(imageView: ImageView, url: String) {
        val context = imageView.context
        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.baseline_crop_original_24)
                .error(R.drawable.baseline_crop_original_24)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.baseline_crop_original_24)
                .into(imageView)
        }
    }
    private fun convertList(str: List<Type>): String{
        val separator = "\n\n"
        var nameType = "Types$separator"
        for (name in str){
            nameType += name.type.name + separator
        }
        return nameType
    }
    private fun statToString(stats: Stat): String{
        return "${stats.stat!!.name}: ${stats.base_stat}"
    }
}