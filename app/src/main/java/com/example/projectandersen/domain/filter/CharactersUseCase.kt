package com.example.projectandersen.domain.filter

import com.example.projectandersen.data.apiservice.LocalRepository
import com.example.projectandersen.data.models.CharactersResult
import com.example.projectandersen.data.apiservice.NetworkConnectionRetrofit2
import com.example.projectandersen.data.models.PaginationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val FILTER_KEY_TYPE = "type"
private const val FILTER_KEY_SPECIES = "species"
private const val FILTER_KEY_GENDER = "gender"
private const val FILTER_KEY_STATUS = "status"
private const val FILTER_KEY_NAME = "name"

interface CharactersUseCase {
    suspend fun getCharactersByFilter(
        page: Int,
        genderFilter: String? = null,
        typeFilter: String? = null,
        speciesFilter: String? = null,
        statusFilter: String? = null,
        nameFilter: String? = null
    ): CharactersResult

    suspend fun getCharacters(isNetworkActive: Boolean, page: Int): CharactersResult
}

class CharactersUseCaseImpl(
    private val localRepository: LocalRepository
) : CharactersUseCase {

    override suspend fun getCharactersByFilter(
        page: Int,
        genderFilter: String?,
        typeFilter: String?,
        speciesFilter: String?,
        statusFilter: String?,
        nameFilter: String?
    ): CharactersResult {
        val mapOfFilter = hashMapOf<String, String>()

        genderFilter?.let {
            mapOfFilter.put(FILTER_KEY_GENDER, it)
        }

        typeFilter?.let {
            mapOfFilter.put(FILTER_KEY_TYPE, it)
        }

        speciesFilter?.let {
            mapOfFilter.put(FILTER_KEY_SPECIES, it)
        }

        statusFilter?.let {
            mapOfFilter.put(FILTER_KEY_STATUS, it)
        }

        nameFilter?.let {
            mapOfFilter.put(FILTER_KEY_NAME, it)
        }

        return NetworkConnectionRetrofit2
            .retrofitCharactersAPI
            .getCharactersByFilter(mapOfFilter, page)
    }

    override suspend fun getCharacters(isNetworkActive: Boolean, page: Int): CharactersResult {
        return if (isNetworkActive) {
            val result = NetworkConnectionRetrofit2
                .retrofitCharactersAPI
                .getAllCharacters(page)
            withContext(Dispatchers.IO) {
                localRepository.addCharacters(result.results)
            }
            result
        } else {
            withContext(Dispatchers.IO) {
                CharactersResult(
                    results = localRepository.getCharacters(),
                    info = PaginationInfo(1)
                )
            }
        }
    }
}