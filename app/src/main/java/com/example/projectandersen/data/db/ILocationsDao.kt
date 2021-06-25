package com.example.projectandersen.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projectandersen.data.models.Locations

@Dao
interface ILocationsDao {
    @Insert
    fun insertAllLocations(locations: List<Locations>)

    @Query("SELECT * FROM locations")
    fun getAllLocations(): List<Locations>
}