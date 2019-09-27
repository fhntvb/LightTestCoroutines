package com.imagine.readtheposts.view.di

object DependencyInjection {
    fun initialize() {
        NetworkModule.initialize()
    }
}