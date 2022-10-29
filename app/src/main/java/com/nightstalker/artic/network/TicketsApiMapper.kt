package com.nightstalker.artic.network


import com.nightstalker.artic.core.local.ticket.TicketStore
import com.nightstalker.artic.features.toListOfTickets
import com.nightstalker.artic.features.toTicket


class TicketsApiMapper(private val api: TicketStore )  {
    suspend fun getTicketById(id: Long) = api.findById(id).toTicket()

    suspend fun getTickets() = api.all().toListOfTickets()
}