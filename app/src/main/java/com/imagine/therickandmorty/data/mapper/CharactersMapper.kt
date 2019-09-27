package com.imagine.therickandmorty.data.mapper

import com.imagine.therickandmorty.data.entity.Character
import com.imagine.therickandmorty.data.entity.CharactersEntity
import com.imagine.therickandmorty.data.entity.Location
import com.imagine.therickandmorty.data.entity.Origin
import com.imagine.therickandmorty.domain.models.CharacterModel
import com.imagine.therickandmorty.domain.models.LocationModel
import com.imagine.therickandmorty.domain.models.OriginModel

class CharactersMapper : Mapper<List<Character>, List<CharacterModel>> {
    override fun map(source: List<Character>): List<CharacterModel> {
        val list = ArrayList<CharacterModel>()
        source.forEach {
            characterEntity -> list.add(getCharacterModel(characterEntity))
        }
        return list
    }

    private fun getCharacterModel(characterEntity: Character): CharacterModel {
        return CharacterModel(
            characterEntity.id,
            characterEntity.name,
            characterEntity.status,
            characterEntity.species,
            characterEntity.gender,
            getOriginModel(characterEntity.origin),
            getLocationModel(characterEntity.location),
            characterEntity.image,
            characterEntity.episode
        )
    }

    private fun getLocationModel(location: Location) = LocationModel(location.name, location.url)

    private fun getOriginModel(origin: Origin) = OriginModel(origin.name, origin.url)
}