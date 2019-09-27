package com.imagine.therickandmorty.domain.models

import java.io.Serializable

data class EpisodeModel(
    val id: String,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) : Serializable