package com.nightstalker.artic.core.local.ticket

import android.util.Log
import com.nightstalker.artic.core.local.database.AppDatabase
import com.nightstalker.artic.features.ticket.domain.TicketUseCase


class TicketStore(database: AppDatabase) {
    private val localTickets = database.getTicketsDao()

    suspend fun all(): List<LocalTicket> =  localTickets.getTickets()
    suspend fun all(term: String): List<LocalTicket> = localTickets.searchAll(term)
    suspend fun findById(id: Long): LocalTicket = localTickets.findById(id)

    suspend fun save(tickets: List<TicketUseCase>) {
        Log.d("Ticket_save"," 1 size =  ${tickets.size}")
        tickets.forEach{
            this.localTickets.insert( it.toLocal())
        }
        Log.d("Ticket_save"," 2 size =  ${tickets.size}")
    }

    private fun TicketUseCase.toLocal() = LocalTicket(
        id = id,
        title = title,
        exhibitionId = exhibitionId,
        galleryId = galleryId,
        galleryTitle = galleryTitle,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
        shortDescription = shortDescription,
        numberOfPersons = numberOfPersons,
        timestamp = timestamp,
    )

    private fun LocalTicket.fromLocal() = TicketUseCase(
        id = id,
        title = title,
        exhibitionId = exhibitionId,
        galleryId = galleryId,
        galleryTitle = galleryTitle,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
        shortDescription = shortDescription,
        numberOfPersons = numberOfPersons,
        timestamp = timestamp,
    )
}