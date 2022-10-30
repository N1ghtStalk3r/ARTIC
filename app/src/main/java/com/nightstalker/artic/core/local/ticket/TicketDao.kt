package com.nightstalker.artic.core.local.ticket

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(localTicket: List<LocalTicket>)

    @Query("SELECT * FROM ticket")
    fun getAll(): List<LocalTicket>

    @Query("SELECT * FROM ticket WHERE upper(title) like  upper('%'||:term||'%')  ")
    fun searchAll(term: String): List<LocalTicket>

    @Query("SELECT * FROM ticket WHERE id = :id  ")
    fun findById(id: Long): LocalTicket

    @Query("SELECT * FROM ticket WHERE id = :id  ")
    suspend fun getTicketById(id: Long): LocalTicket

    @Query("SELECT * FROM ticket")
    fun getTickets(): List<LocalTicket>



}