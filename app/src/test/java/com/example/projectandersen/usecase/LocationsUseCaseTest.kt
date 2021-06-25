package com.example.projectandersen.usecase

import com.example.projectandersen.data.apiservice.LocalRepository
import com.example.projectandersen.domain.filter.LocationsUseCase
import com.example.projectandersen.domain.filter.LocationsUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class LocationsUseCaseTest {

    private val localRepository: LocalRepository = Mockito.mock(LocalRepository::class.java)

    @Test
    fun `when get locations without network usecase should call get locations from repository`() {
        runBlocking {
            val locationsUseCase = getLocationsUseCase()
            locationsUseCase.getLocations(false, 1)
            Mockito.verify(localRepository).getLocations()
        }
    }

    private fun getLocationsUseCase(): LocationsUseCase {
        return LocationsUseCaseImpl(localRepository)
    }
}