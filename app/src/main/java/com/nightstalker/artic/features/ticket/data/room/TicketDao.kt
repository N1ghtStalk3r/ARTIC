package com.nightstalker.artic.features.ticket.data.room

import androidx.room.*
import com.nightstalker.artic.features.ApiConstants.DB_TABLE_TICKETS

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: LocalTicketDbEntity)

    @Query("DELETE FROM ${DB_TABLE_TICKETS} WHERE id = :ticketId OR exhibition_id = :exhibitionId ")
    suspend fun deleteTicket(ticketId: Long, exhibitionId: String)

    @Update
    suspend fun updateTicket(ticket: LocalTicketDbEntity)

    @Query("SELECT * FROM ${DB_TABLE_TICKETS}  WHERE id = :key")
    suspend fun getTicketById(key: Long): LocalTicketDbEntity

    @Query("SELECT * FROM ${DB_TABLE_TICKETS}")
    suspend fun getAllTickets(): List<LocalTicketDbEntity>

    @Query("SELECT * FROM ${DB_TABLE_TICKETS} WHERE upper(title) like  upper('%'||:term||'%')  ")
    fun searchTicketsByTerm(term: String): List<LocalTicketDbEntity>

}