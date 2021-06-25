package com.example.projectandersen.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projectandersen.data.Episodes

@Dao
interface IEpisodesDao {
    @Insert
    fun insertAllEpisodes(episodes: List<Episodes>)

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes() : List<Episodes>
}