package com.nightstalker.artic.core.local.ticket

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nightstalker.artic.network.ApiConstants.DB_TABLE_TICKETS

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(ticket: LocalTicket)

    @Query("DELETE FROM ${DB_TABLE_TICKETS} WHERE id = :ticketId OR exhibition_id = :exhibitionId ")
    suspend fun remove(ticketId: Long, exhibitionId: String)

    @Update
    suspend fun update(ticket: LocalTicket)

    @Query("SELECT * FROM ${DB_TABLE_TICKETS}")
    fun getAll(): LiveData<List<LocalTicket>>

    @Query("SELECT * FROM ${DB_TABLE_TICKETS}  WHERE id = :key")
    suspend fun get(key: Long): LocalTicket

    @Query("SELECT * FROM ${DB_TABLE_TICKETS}")
    suspend fun getTickets(): List<LocalTicket>

    @Query("SELECT * FROM ${DB_TABLE_TICKETS} WHERE upper(title) like  upper('%'||:term||'%')  ")
    fun searchAll(term: String): List<LocalTicket>

    @Query("SELECT * FROM ${DB_TABLE_TICKETS} WHERE id = :id  ")
    suspend fun findById(id: Long): LocalTicket

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(localTicket: LocalTicket)

}