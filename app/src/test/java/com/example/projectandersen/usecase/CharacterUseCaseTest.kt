package com.example.projectandersen.usecase

import com.example.projectandersen.data.apiservice.LocalRepository
import com.example.projectandersen.domain.filter.CharactersUseCase
import com.example.projectandersen.domain.filter.CharactersUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class CharacterUseCaseTest {

    private val localRepository: LocalRepository = Mockito.mock(LocalRepository::class.java)

    @Test
    fun `when get characters without network usecase should call get characters from repository`() {
        runBlocking {
            val charactersUseCase = getCharactersUseCase()
            charactersUseCase.getCharacters(false, 1)
            Mockito.verify(localRepository).getCharacters()
        }
    }

    private fun getCharactersUseCase(): CharactersUseCase {
        return CharactersUseCaseImpl(localRepository)
    }
}