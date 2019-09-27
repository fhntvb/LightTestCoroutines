package com.imagine.therickandmorty.data.mapper

interface Mapper<S, R> {

    fun map(source: S): R
}