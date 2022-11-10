package com.nightstalker.artic.core.local.ticket

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(ticket: LocalTicket)

    @Query("DELETE FROM tickets WHERE id = :ticketId OR exhibition_id = :exhibitionId ")
    suspend fun remove(ticketId: Long, exhibitionId: String)

    @Update
    suspend fun update(ticket: LocalTicket)

    @Query("SELECT * FROM tickets")
    fun getAll() : LiveData<List<LocalTicket>>

    @Query("SELECT * FROM tickets WHERE id = :key")
    suspend fun get(key: Long) : LocalTicket

    @Query("SELECT * FROM tickets")
    suspend fun getTickets(): List<LocalTicket>

    @Query("SELECT * FROM tickets WHERE upper(title) like  upper('%'||:term||'%')  ")
    fun searchAll(term: String): List<LocalTicket>

    @Query("SELECT * FROM tickets WHERE id = :id  ")
    suspend fun findById(id: Long): LocalTicket

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(localTicket: LocalTicket)

}