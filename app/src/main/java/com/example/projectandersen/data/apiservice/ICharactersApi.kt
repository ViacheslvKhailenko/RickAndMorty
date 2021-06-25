package com.example.projectandersen.data.apiservice

import com.example.projectandersen.data.models.CharactersResult
import com.example.projectandersen.data.models.SingleCharacter
import retrofit2.http.*

interface ICharactersAPI {
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int): CharactersResult

    @GET("character")
    suspend fun getCharactersByFilter(
        @QueryMap filterMap: Map<String, String>,
        @Query("page") page: Int
    ): CharactersResult

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") id: Int): SingleCharacter

    @GET
    suspend fun getSingleCharacterByUrl(@Url url: String): SingleCharacter
}