package com.imagine.therickandmorty.data.network

import android.location.Location

object ApiConstants {
    /* server methods */
    const val CHARACTERS = "character"
    const val LOCATION = "location"
    const val EPISODE = "episode"
    const val EPISODE_MULTIPLE = "episode/{episodesIds}"
    const val EPISODE_SINGLE = "episode/{episodeId}"
    const val CHARACTER_MULTIPLE = "character/{characterIds}"
    const val CHARACTER_SINGLE = "character/{characterid}"

    /* server method parameters */
    const val name = "name"
    const val status = "status"
    const val gender = "gender"
    const val page = "page"
    const val count = "count"
}