package com.example.projectandersen.domain.filter

import com.example.projectandersen.data.apiservice.LocalRepository
import com.example.projectandersen.data.apiservice.NetworkConnectionRetrofit2
import com.example.projectandersen.data.models.LocationInfo
import com.example.projectandersen.data.models.LocationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LocationsUseCase {
    suspend fun getLocations(isNetworkActive: Boolean, page: Int): LocationResult
}

class LocationsUseCaseImpl(
    private val localRepository: LocalRepository
) : LocationsUseCase {

    override suspend fun getLocations(isNetworkActive: Boolean, page: Int): LocationResult {
        return if (isNetworkActive) {
            val result = NetworkConnectionRetrofit2
                .retrofitLocationsAPI
                .getAllLocations(page)
            withContext(Dispatchers.IO) {
                localRepository.addLocations(result.results)
            }
            result
        } else {
            withContext(Dispatchers.IO) {
                LocationResult(
                    results = localRepository.getLocations(),
                    info = LocationInfo(1, 1)
                )
            }
        }
    }
}