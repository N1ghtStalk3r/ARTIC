package com.nightstalker.artic.features.ticket.data


import com.nightstalker.artic.features.ticket.domain.Ticket
import com.nightstalker.artic.features.ticket.domain.repo.TicketsRepo
import com.nightstalker.artic.network.TicketsApiMapper

class TicketsRepoImpl (private val apiMapper: TicketsApiMapper
) : TicketsRepo {
    override suspend fun getTicketById(id: Long): Ticket =
        apiMapper.getTicketById(id)

    override suspend fun getTickets(): List<Ticket> =
        apiMapper.getTickets()
}