package com.nightstalker.artic.network


import com.nightstalker.artic.core.local.ticket.TicketStore
import com.nightstalker.artic.features.toListOfTicketUseCase
import com.nightstalker.artic.features.toTicketUseCase


class TicketsApiMapper(private val api: TicketStore) {
    suspend fun getTicketById(id: Long) = api.findById(id).toTicketUseCase()
    suspend fun getTickets() = api.all().toListOfTicketUseCase()
}