package com.example.projectandersen.data.db

import androidx.room.*
import com.example.projectandersen.data.models.Characters

@Dao
interface ICharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characters: Characters)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCharacters(list: List<Characters>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : List<Characters>
}