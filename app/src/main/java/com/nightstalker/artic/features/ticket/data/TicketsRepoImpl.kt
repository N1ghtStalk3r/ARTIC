package com.nightstalker.artic.features.ticket.data


import com.nightstalker.artic.features.ticket.domain.TicketUseCase
import com.nightstalker.artic.features.ticket.domain.repo.TicketsRepo
import com.nightstalker.artic.network.TicketsApiMapper

class TicketsRepoImpl (private val apiMapper: TicketsApiMapper
) : TicketsRepo {
    override suspend fun getTicketById(id: Long): TicketUseCase =
        apiMapper.getTicketById(id)

    override suspend fun getTickets(): List<TicketUseCase> =
        apiMapper.getTickets()
}