package com.example.projectandersen.usecase

import com.example.projectandersen.data.apiservice.LocalRepository
import com.example.projectandersen.domain.filter.EpisodesUseCase
import com.example.projectandersen.domain.filter.EpisodesUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.*

class EpisodesUseCaseTest {

    private val localRepository: LocalRepository = mock(LocalRepository::class.java)

    @Test
    fun `when get episodes without network usecase should call get episodes from repository`() {
        runBlocking {
            val episodesUseCase = getEpisodesUseCase()
            episodesUseCase.getEpisodes(false, 1)
            verify(localRepository).getEpisodes()
        }
    }

    private fun getEpisodesUseCase(): EpisodesUseCase {
        return EpisodesUseCaseImpl(localRepository)
    }
}