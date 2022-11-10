package com.nightstalker.artic.core.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nightstalker.artic.core.local.ticket.LocalTicket
import com.nightstalker.artic.core.local.ticket.TicketDao

@Database(  entities = [LocalTicket::class], version = 1, exportSchema = false,  )

abstract class AppDatabase : RoomDatabase() {

    abstract fun getTicketsDao(): TicketDao
}