package com.example.pokemon.viewModels.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.pokemon.R
import com.example.pokemon.model.data.Poke
import com.example.pokemon.model.data.PokemonStat
import com.example.pokemon.model.data.PokemonType
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class DetailsHolder(itemView: View) {

    private val itemView: ImageView = itemView.findViewById(R.id.imageView)
    private val nameText: TextView = itemView.findViewById(R.id.name)
    private val types: TextView = itemView.findViewById(R.id.types)
    private val weight: TextView = itemView.findViewById(R.id.weight)
    private val height: TextView = itemView.findViewById(R.id.height)
    private val hp: TextView = itemView.findViewById(R.id.hp)
    private val attack: TextView = itemView.findViewById(R.id.attack)
    private val defense: TextView = itemView.findViewById(R.id.defense)
    private val specialAttack: TextView = itemView.findViewById(R.id.specialAttack)
    private val specialDefense: TextView = itemView.findViewById(R.id.specialDefense)
    private val speed: TextView = itemView.findViewById(R.id.speed)

    private fun ImageView.loadSvg(url: String?) {
        GlideToVectorYou
            .init()
            .with(this.context)
            .setPlaceHolder(R.drawable.ic_load_picture, R.drawable.ic_load_picture)
            .load(Uri.parse(url), this)
    }
    private fun convertList(str: List<PokemonType>): String{
        val separator = "\n\n"
        var nameType = "Types$separator"
        for (name in str){
            nameType += name.type.name + separator
        }
        return nameType
    }
    private fun statToString(stats: PokemonStat): String{
        return "${stats.stat!!.name}: ${stats.baseStat}"
    }

    fun bind(pokemon: Poke) {
        itemView.loadSvg("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/${pokemon.id}.svg")
        nameText.text = pokemon.name
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