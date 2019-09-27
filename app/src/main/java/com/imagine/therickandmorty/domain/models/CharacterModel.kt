package com.imagine.therickandmorty.domain.models

import java.io.Serializable

data class CharacterModel (
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: OriginModel,
    val location: LocationModel,
    val image: String,
    val episode: List<String>
) : Serializable

data class OriginModel(
    val name: String,
    val url: String
) : Serializable

data class LocationModel(
    val name: String,
    val url: String
) : Serializable