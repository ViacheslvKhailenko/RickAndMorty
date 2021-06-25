package com.example.projectandersen.domain.filter

import com.example.projectandersen.data.EpisodesResult
import com.example.projectandersen.data.apiservice.NetworkConnectionRetrofit2

private const val FILTER_KEY_NAME = "name"
private const val FILTER_KEY_EPISODE = "episode"

class EpisodesFilterUseCase {

    suspend fun getEpisodesByFilter(
        page: Int,
        nameFilter: String? = null,
        episodeFilter: String? = null,
    ): EpisodesResult {
        val mapOfFilter = hashMapOf<String, String>()
        nameFilter?.let {
            mapOfFilter.put(FILTER_KEY_NAME, it)
        }

        episodeFilter?.let {
            mapOfFilter.put(FILTER_KEY_EPISODE, it)
        }

        return NetworkConnectionRetrofit2
            .retrofitEpisodesAPI
            .getEpisodesByFilter(mapOfFilter, page)
    }
}
