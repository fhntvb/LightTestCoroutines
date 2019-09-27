package com.imagine.therickandmorty.data.repository

import com.imagine.therickandmorty.domain.models.CharacterModel
import com.imagine.therickandmorty.domain.models.EpisodeModel

interface Repository {
    suspend fun getCharacters(page: Int): List<CharacterModel>
    suspend fun filterCharacters(name: String): List<CharacterModel>
    suspend fun getEpisodesForCharacter(episode: List<String>): List<EpisodeModel>
    suspend fun getCharactersForEpisode(characters : List<String>): List<CharacterModel>
}