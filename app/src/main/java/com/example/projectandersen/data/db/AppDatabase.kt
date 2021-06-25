package com.example.projectandersen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectandersen.data.Episodes
import com.example.projectandersen.data.models.Characters
import com.example.projectandersen.data.models.Locations

@Database(entities = [Characters::class, Episodes::class, Locations::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): ICharactersDao
    abstract fun episodesDao(): IEpisodesDao
    abstract fun locationsDao(): ILocationsDao
}