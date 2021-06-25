package com.example.projectandersen.data.apiservice

import com.example.projectandersen.data.Episodes
import com.example.projectandersen.data.db.AppDatabase
import com.example.projectandersen.data.models.Characters
import com.example.projectandersen.data.models.Locations

interface LocalRepository {
    fun addCharacters(list: List<Characters>)
    fun getCharacters(): List<Characters>
    fun addEpisodes(list: List<Episodes>)
    fun getEpisodes(): List<Episodes>
    fun addLocations(list: List<Locations>)
    fun getLocations(): List<Locations>
}

class LocalRepositoryImpl (
    private val appDatabase: AppDatabase
) : LocalRepository {
    override fun getCharacters(): List<Characters> {
        return appDatabase.charactersDao().getAllCharacters()
    }

    override fun addCharacters(list: List<Characters>) {
        appDatabase.charactersDao().insertAllCharacters(list)
    }

    override fun getEpisodes(): List<Episodes> {
        return appDatabase.episodesDao().getAllEpisodes()
    }

    override fun addEpisodes(list: List<Episodes>) {
        appDatabase.episodesDao().insertAllEpisodes(list)
    }

    override fun getLocations(): List<Locations> {
        return appDatabase.locationsDao().getAllLocations()
    }

    override fun addLocations(list: List<Locations>) {
        appDatabase.locationsDao().insertAllLocations(list)
    }
}