package com.example.projectandersen.domain.di

import com.example.projectandersen.domain.filter.*
import org.koin.dsl.module

val useCaseModule = module {
    single<CharactersUseCase> {
        CharactersUseCaseImpl(get())
    }

    single<EpisodesUseCase> {
        EpisodesUseCaseImpl(get())
    }

    single<LocationsUseCase> {
        LocationsUseCaseImpl(get())
    }
}