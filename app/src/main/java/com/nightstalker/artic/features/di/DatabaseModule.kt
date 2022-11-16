package com.nightstalker.artic.features.di

import androidx.room.Room
import com.nightstalker.artic.core.local.database.AppDatabase
import com.nightstalker.artic.core.local.ticket.TicketDao
import org.koin.dsl.module


val databaseModule = module {
    // Room Database
    single<AppDatabase> {
        Room
            .databaseBuilder(get(), AppDatabase::class.java, "artic")
            .build()
    }
    // LocalTicketsDAO
    single<TicketDao> { get<AppDatabase>().getTicketsDao() }
}
