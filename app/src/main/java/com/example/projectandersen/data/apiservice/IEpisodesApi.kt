package com.example.projectandersen.data.apiservice

import com.example.projectandersen.data.EpisodesResult
import com.example.projectandersen.data.models.SingleEpisode
import retrofit2.http.*

interface IEpisodesApi {
    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") page: Int): EpisodesResult

    @GET("episode")
    suspend fun getEpisodesByFilter(
        @QueryMap filterMap: Map<String, String>,
        @Query("page") page: Int
    ): EpisodesResult

    @GET("episode/{id}")
    suspend fun getSingleEpisode(@Path("id") id: Int): SingleEpisode

    @GET
    suspend fun getSingleEpisodeByUrl(@Url url: String): SingleEpisode
}