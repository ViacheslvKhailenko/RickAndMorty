package com.example.projectandersen.data.di

import androidx.room.Room
import com.example.projectandersen.data.apiservice.LocalRepository
import com.example.projectandersen.data.apiservice.LocalRepositoryImpl
import com.example.projectandersen.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "database-name"
        ).build()
    }

    single<LocalRepository> {
        LocalRepositoryImpl(get())
    }
}