package com.nightstalker.artic.core.local.ticket

import android.util.Log
import com.nightstalker.artic.core.local.database.AppDatabase
import com.nightstalker.artic.features.ticket.domain.TicketUseCase

class TicketStore(database: AppDatabase) {
    private val localTickets = database.getTicketsDao()
    suspend fun all(): List<LocalTicket> = localTickets.getTickets()
    suspend fun findById(id: Long): LocalTicket = localTickets.findById(id)
}