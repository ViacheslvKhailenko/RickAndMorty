package com.example.projectandersen.domain.filter

import com.example.projectandersen.data.EpisodesPagination
import com.example.projectandersen.data.EpisodesResult
import com.example.projectandersen.data.apiservice.LocalRepository
import com.example.projectandersen.data.apiservice.NetworkConnectionRetrofit2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface EpisodesUseCase {
    suspend fun getEpisodes(isNetworkActive: Boolean, page: Int): EpisodesResult
}

class EpisodesUseCaseImpl(
    private val localRepository: LocalRepository
) : EpisodesUseCase {

    override suspend fun getEpisodes(isNetworkActive: Boolean, page: Int): EpisodesResult {
        return if (isNetworkActive) {
            val result = NetworkConnectionRetrofit2
                .retrofitEpisodesAPI
                .getAllEpisodes(page)
            withContext(Dispatchers.IO) {
                localRepository.addEpisodes(result.results)
            }
            result
        } else {
            withContext(Dispatchers.IO) {
                EpisodesResult(
                    results = localRepository.getEpisodes(),
                    info = EpisodesPagination(1, 1)
                )
            }
        }
    }
}