package com.imagine.therickandmorty.data.utils

class RequestUtils {
    fun createPathFromIds(episodes : List<String>) : String {
        var episodesIds = ""
        episodes.forEach {
            val episodeId = getIdFromUrl(it)
            episodesIds += "$episodeId,"
        }
        return episodesIds.substring(0, episodesIds.length -1)
    }

    fun getIdFromUrl(url: String): String {
        val lastIndesOfSlash = url.lastIndexOf("/") + 1
        val lastIndex = url.length
        return url.subSequence(lastIndesOfSlash, lastIndex).toString()
    }
}