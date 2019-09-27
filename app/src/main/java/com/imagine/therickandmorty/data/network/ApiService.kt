package com.imagine.therickandmorty.data.network

import com.imagine.therickandmorty.data.entity.Character
import com.imagine.therickandmorty.data.entity.CharactersEntity
import com.imagine.therickandmorty.data.entity.EpisodeEntity
import com.imagine.therickandmorty.data.network.ApiConstants.CHARACTERS
import com.imagine.therickandmorty.data.network.ApiConstants.CHARACTER_MULTIPLE
import com.imagine.therickandmorty.data.network.ApiConstants.CHARACTER_SINGLE
import com.imagine.therickandmorty.data.network.ApiConstants.EPISODE_MULTIPLE
import com.imagine.therickandmorty.data.network.ApiConstants.EPISODE_SINGLE
import kotlinx.coroutines.Deferred
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(CHARACTERS)
    fun getCharactersAsync(@Query("page") page: Int): Deferred<Response<CharactersEntity>>

    @GET(CHARACTERS)
    fun filterCharactersAsync(@Query("name") page: String): Deferred<Response<CharactersEntity>>

    @GET(EPISODE_MULTIPLE)
    fun getMultipleEpisodesAsync(@Path("episodesIds") episodes: String): Deferred<Response<List<EpisodeEntity>>>

    @GET(EPISODE_SINGLE)
    fun getSingleEpisodeAsync(@Path("episodeId") episodes: String): Deferred<Response<EpisodeEntity>>

    @GET(CHARACTER_MULTIPLE)
    fun getMultipleCharactersAsync(@Path("characterIds") episodes: String): Deferred<Response<List<Character>>>

    @GET(CHARACTER_SINGLE)
    fun getSingleCharacterAsync(@Path("characterid") episodes: String): Deferred<Response<Character>>
}