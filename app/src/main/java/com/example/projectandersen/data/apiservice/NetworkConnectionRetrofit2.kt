package com.example.projectandersen.data.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConnectionRetrofit2 {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val retrofitCharactersAPI: ICharactersAPI by lazy {
        retrofit().create(ICharactersAPI::class.java)
    }

    val retrofitEpisodesAPI: IEpisodesApi by lazy {
        retrofit().create(IEpisodesApi::class.java)
    }

    val retrofitLocationsAPI: ILocationsApi by lazy {
        retrofit().create(ILocationsApi::class.java)
    }
}

