package com.nightstalker.artic.features.ticket.domain.repo

import com.nightstalker.artic.features.ticket.domain.TicketUseCase


/**
 * Репозиторий для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */

interface TicketsRepo {
    suspend fun getTicketById(id: Long): TicketUseCase?

    suspend fun getTickets(): List<TicketUseCase>?

}