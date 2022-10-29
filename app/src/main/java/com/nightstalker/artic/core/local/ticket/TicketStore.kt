package com.nightstalker.artic.core.local.ticket

import android.util.Log
import com.nightstalker.artic.core.local.database.AppDatabase
import com.nightstalker.artic.features.ticket.domain.Ticket


class TicketStore(database: AppDatabase) {
    private val localTickets = database.tickets

    fun all(): List<LocalTicket> =  localTickets.getAll()
    fun all(term: String): List<LocalTicket> = localTickets.searchAll(term)
    fun findById(id: Long): LocalTicket = localTickets.findById(id)

    suspend fun save(tickets: List<Ticket>) {
        Log.d("Ticket_save"," 1 size =  ${tickets.size}")
        this.localTickets.insert(tickets.map { it.toLocal() })
        Log.d("Movie_save"," 2 size =  ${tickets.size}")
    }

     private fun Ticket.toLocal() = LocalTicket(
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

     private fun LocalTicket.fromLocal() = Ticket(
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