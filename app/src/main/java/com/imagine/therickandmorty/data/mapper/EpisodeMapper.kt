package com.imagine.therickandmorty.data.mapper

import com.imagine.therickandmorty.data.entity.EpisodeEntity
import com.imagine.therickandmorty.domain.models.EpisodeModel

class EpisodeMapper : Mapper<List<EpisodeEntity>, List<EpisodeModel>> {

    override fun map(source: List<EpisodeEntity>): List<EpisodeModel> {
        val list = ArrayList<EpisodeModel>()
        source.forEach {
                characterEntity -> list.add(getEpisodeModel(characterEntity))
        }
        return list
    }

    private fun getEpisodeModel(episodeEntityEntity: EpisodeEntity): EpisodeModel {
        return EpisodeModel(
            episodeEntityEntity.id,
            episodeEntityEntity.name,
            episodeEntityEntity.airDate,
            episodeEntityEntity.episode,
            episodeEntityEntity.characters,
            episodeEntityEntity.url,
            episodeEntityEntity.created)
    }
}