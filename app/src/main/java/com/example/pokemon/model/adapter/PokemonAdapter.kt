package com.example.pokemon.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.R
import com.example.pokemon.databinding.ItemPokemonBinding
import com.example.pokemon.model.data.entities.NamedApiResource

interface OnClickPokemonListener {
    fun onClick(id: Int)
}
class PokemonAdapter(
    private val onClickListener: OnClickPokemonListener
) : PagingDataAdapter<NamedApiResource, PokemonAdapter.Holder>(ProductsDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val pokemon = getItem(position) ?: return
        with (holder.binding) {
            nameTV.text = pokemon.name
            val id = Regex("[0-9]+").findAll(pokemon.url)
                .map(MatchResult::value)
                .toList()

            val imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id[1]}.png"
            loadUserPhoto(imageIV, imageURL)

            holder.itemView.setOnClickListener {
                onClickListener.onClick(
                    id[1].toInt()
                ) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun loadUserPhoto(imageView: ImageView, url: String) {
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

    class Holder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

}

class ProductsDiffCallback : DiffUtil.ItemCallback<NamedApiResource>() {
    override fun areItemsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
        return oldItem == newItem
    }
}