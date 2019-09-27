package com.imagine.therickandmorty.view.characters.listeners

import com.imagine.therickandmorty.domain.models.EpisodeModel

interface OnEpisodeClickListener {
    fun onEpisodeClicked(episode: EpisodeModel)
}