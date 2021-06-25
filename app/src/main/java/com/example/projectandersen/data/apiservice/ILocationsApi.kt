package com.example.projectandersen.data.apiservice

import com.example.projectandersen.data.models.LocationResult
import com.example.projectandersen.data.models.SingleLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ILocationsApi {
    @GET("location")
    suspend fun getAllLocations(@Query("page") page: Int): LocationResult

    @GET("location/{id}")
    suspend fun getSingleLocation(@Path("id") id: Int): SingleLocation
}