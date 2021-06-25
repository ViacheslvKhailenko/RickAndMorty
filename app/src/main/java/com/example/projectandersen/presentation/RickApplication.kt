package com.example.projectandersen.presentation

import android.app.Application
import com.example.projectandersen.data.di.repositoryModule
import com.example.projectandersen.domain.di.useCaseModule
import com.example.projectandersen.presentation.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RickApplication)
            modules(listOf(useCaseModule, presenterModule, repositoryModule))
        }
    }
}