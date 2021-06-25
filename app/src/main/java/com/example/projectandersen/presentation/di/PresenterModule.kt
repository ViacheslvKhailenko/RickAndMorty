package com.example.projectandersen.presentation.di

import com.example.projectandersen.presentation.characters.CharactersPresenter
import com.example.projectandersen.presentation.episodes.EpisodesPresenter
import com.example.projectandersen.presentation.locations.LocationsPresenter
import org.koin.dsl.module

val presenterModule = module {
    single { CharactersPresenter(get()) }

    single { LocationsPresenter(get()) }

    single { EpisodesPresenter(get()) }
}