package com.nightstalker.artic

import android.app.Application
import com.nightstalker.artic.core.di.dispatchersModule
import com.nightstalker.artic.features.artwork.di.artworkModules
import com.nightstalker.artic.features.di.networkModule
import com.nightstalker.artic.features.exhibition.di.exhibitionModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ArticApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ArticApp)
            modules(dispatchersModule)
            modules(artworkModules)
            modules(exhibitionModules)
            modules(networkModule)
        }
    }
}