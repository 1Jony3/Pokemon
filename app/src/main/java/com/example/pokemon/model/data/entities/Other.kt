package com.example.pokemon.model.data.entities

import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("official-artwork")
    val official_artwork: OfficialArtwork
)