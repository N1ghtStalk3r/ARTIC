package com.nightstalker.artic.core.local.ticket

import com.nightstalker.artic.core.local.database.AppDatabase

class TicketStore(database: AppDatabase) {
    private val localTickets = database.getTicketsDao()
    suspend fun all(): List<LocalTicket> = localTickets.getTickets()
    suspend fun findById(id: Long): LocalTicket = localTickets.findById(id)
}