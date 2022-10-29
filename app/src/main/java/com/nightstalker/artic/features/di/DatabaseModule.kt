package com.nightstalker.artic.features.di

import com.nightstalker.artic.core.local.database.AppDatabase
import org.koin.dsl.module


val databaseModule = module {


    // Room Database
    single { AppDatabase.getInstance(get()) }
    // TicketsDAO
    single { get<AppDatabase>().getTicketsDao() }

}
