package com.imagine.therickandmorty

import android.app.Application
import com.imagine.readtheposts.view.di.DependencyInjection

class RickAndMortyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DependencyInjection.initialize()
    }
}