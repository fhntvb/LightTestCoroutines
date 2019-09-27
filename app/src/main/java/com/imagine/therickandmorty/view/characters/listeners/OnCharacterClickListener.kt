package com.imagine.therickandmorty.view.characters.listeners

import com.imagine.therickandmorty.domain.models.CharacterModel

interface OnCharacterClickListener {
    fun onCharacterClicked(character: CharacterModel)
}