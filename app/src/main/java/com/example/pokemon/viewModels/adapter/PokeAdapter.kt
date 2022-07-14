package com.example.pokemon.viewModels.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.model.data.NamedApiResource
import com.example.pokemon.view.MainActivity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou


class PokeAdapter(pokemons: List<NamedApiResource>, selected: Boolean): RecyclerView.Adapter<PokeAdapter.PokeHolder>() {
    private var pokemon: List<NamedApiResource> = pokemons
    private var selectedPosition: Boolean = selected

    fun setSelectedPosition(selected: Boolean){
        selectedPosition = selected
    }
    fun setPokemon(pokemons: List<NamedApiResource>){
        pokemon = pokemons
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poke_item, parent, false)
        return PokeHolder(view)
    }

    override fun onBindViewHolder(holder: PokeHolder, position: Int) {
        val pokemon = pokemon[position]
        holder.bind(pokemon, position, selectedPosition)
    }

    override fun getItemCount() = pokemon.size

    class PokeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.name_text)
        private val image: ImageView = itemView.findViewById(R.id.image_poke)

        private fun ImageView.loadSvg(url: String?) {
            GlideToVectorYou
                .init()
                .with(this.context)
                .setPlaceHolder(R.drawable.ic_load_picture, R.drawable.ic_load_picture)
                .load(Uri.parse(url), this)
        }

        private fun changeColor(selectedPosition: Boolean) {
            if (selectedPosition)
                nameText.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        (R.color.transparentBackgroundYellow)
                    )
                )
            else nameText.setTextColor(ContextCompat.getColor(itemView.context, (R.color.letter)))
        }
        fun bind(pokemon: NamedApiResource, position: Int, selectedPosition: Boolean) {
            nameText.text = pokemon.name
            val id = Regex("[0-9]+").findAll(pokemon.url)
                .map(MatchResult::value)
                .toList()
            val imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/${id[1]}.svg"
            image.loadSvg(imageURL)
            itemView.setOnClickListener {
                (it.context as MainActivity).showDetails(pokemon)
            }
            if (position == 0) changeColor(selectedPosition)
        }
    }

}