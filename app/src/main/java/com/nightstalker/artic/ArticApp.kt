package com.nightstalker.artic

import android.app.Application
import com.nightstalker.artic.core.di.dispatchersModule
import com.nightstalker.artic.features.artwork.di.artworkModules
import com.nightstalker.artic.features.di.databaseModule
import com.nightstalker.artic.features.di.networkModule
import com.nightstalker.artic.features.exhibition.di.exhibitionModules
import com.nightstalker.artic.features.ticket.di.ticketModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class ArticApp : Application() {
//    private val database by lazy {
//        AppDatabase.getInstance(this@ArticApp)
//    }


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ArticApp)
            modules(dispatchersModule)
            modules(artworkModules)
            modules(exhibitionModules)
            modules(networkModule)
            modules(databaseModule)
            modules(ticketModules)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }


}