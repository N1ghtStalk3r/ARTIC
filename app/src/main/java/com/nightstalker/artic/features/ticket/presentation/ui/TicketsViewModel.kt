package com.nightstalker.artic.features.ticket.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.core.local.ticket.LocalTicket
import com.nightstalker.artic.core.local.ticket.TicketDao
import com.nightstalker.artic.features.ticket.domain.TicketUseCase

import com.nightstalker.artic.features.ticket.domain.repo.TicketsRepo
import com.nightstalker.artic.features.toLocalTicket
import com.nightstalker.artic.features.toTicketUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * ViewModel для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */

class TicketsViewModel(private val dao: TicketDao)  : ViewModel() {
    private var _ticketLoaded = MutableLiveData<TicketUseCase>()
    val ticketLoaded: LiveData<TicketUseCase> get() = _ticketLoaded

    private var _ticketsLoaded = MutableLiveData<List<TicketUseCase>>()
    val ticketsLoaded: LiveData<List<TicketUseCase>> get() = _ticketsLoaded

    fun getTicket(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _ticketLoaded.postValue(
                dao.get(id).toTicketUseCase()
            )
        }
    }

    fun deleteTicket(ticketId: Long, exhibitionId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.remove(ticketId = ticketId ,  exhibitionId = exhibitionId)
        }
    }
    fun saveTicket(ticket: TicketUseCase) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.save(ticket = ticket.toLocalTicket())
        }
    }


    fun getAllTickets() {
        viewModelScope.launch(Dispatchers.IO) {
            _ticketsLoaded.postValue(
                dao.getTickets().map { it.toTicketUseCase() } ?: listOf(TicketUseCase())
            )
        }
    }




}