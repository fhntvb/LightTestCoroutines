package com.imagine.therickandmorty.data.repository

import com.imagine.therickandmorty.data.mapper.CharactersMapper
import com.imagine.therickandmorty.data.mapper.EpisodeMapper
import com.imagine.therickandmorty.data.network.ApiService
import com.imagine.therickandmorty.data.utils.RequestUtils
import com.imagine.therickandmorty.domain.models.CharacterModel
import com.imagine.therickandmorty.domain.models.EpisodeModel

class RickAndMortyRepository(
    private val apiService: ApiService,
    private val requestUtils: RequestUtils,
    private val charactersMapper: CharactersMapper,
    private val episodeMapper: EpisodeMapper

) : Repository {
    override suspend fun getCharacters(page: Int): List<CharacterModel> {
        val result = apiService.getCharactersAsync(page).await()
        val entities = result.body()

        return if (entities != null)
            charactersMapper.map(entities.results)
        else
            emptyList()
    }

    override suspend fun filterCharacters(name: String): List<CharacterModel> {
        val result = apiService.filterCharactersAsync(name).await()
        val entity = result.body()

        return if (entity != null)
            charactersMapper.map(entity.results)
        else
            emptyList()
    }

    override suspend fun getEpisodesForCharacter(episode: List<String>) =
        if (episode.size > 1) getMultipleEpisodes(episode) else getSingleEpisode(episode)

    private suspend fun getMultipleEpisodes(episode: List<String>) : List<EpisodeModel> {
        var path = requestUtils.createPathFromIds(episode)
        var result = apiService.getMultipleEpisodesAsync(path).await()
        val entity = result.body()
        return if (entity != null)
            episodeMapper.map(entity)
        else
            emptyList()
    }

    private suspend fun getSingleEpisode(episode: List<String>) : List<EpisodeModel> {
        var path = requestUtils.createPathFromIds(episode)
        var result = apiService.getSingleEpisodeAsync(path).await()
        val entity = result.body()
        return if (entity != null)
            episodeMapper.map(mutableListOf(entity))
        else
            emptyList()
    }

    override suspend fun getCharactersForEpisode(characters: List<String>): List<CharacterModel> =
        if (characters.size > 1) getMultipleCharacters(characters) else getSingleCharacter(characters)

    private suspend fun getMultipleCharacters(characters: List<String>) : List<CharacterModel> {
        var path = requestUtils.createPathFromIds(characters)
        var result = apiService.getMultipleCharactersAsync(path).await()
        val entity = result.body()
        return if (entity != null)
            charactersMapper.map(entity)
        else
            emptyList()
    }

    private suspend fun getSingleCharacter(characters: List<String>) : List<CharacterModel> {
        var path = requestUtils.createPathFromIds(characters)
        var result = apiService.getSingleCharacterAsync(path).await()
        val entity = result.body()
        return if (entity != null)
            charactersMapper.map(mutableListOf(entity))
        else
            emptyList()
    }
}