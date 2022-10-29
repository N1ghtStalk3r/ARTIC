package com.nightstalker.artic.core.local.ticket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ticket")
data class LocalTicket(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
        val id: Long = 0L,
    @ColumnInfo(name = "title")
        val title: String = "",
    @ColumnInfo(name = "exhibition_id")
        val exhibitionId: String = "",
    @ColumnInfo(name = "gallery_id")
        val galleryId: Int = 0,
    @ColumnInfo(name = "gallery_title")
        val galleryTitle: String = "",
    @ColumnInfo(name = "aic_end_at")
        val aicEndAt: String = "",
    @ColumnInfo(name = "aic_start_at")
        val aicStartAt: String = "",

    @ColumnInfo(name = "shortDescription")
        val shortDescription: String = "",
    @ColumnInfo(name = "number_of_persons")
        val numberOfPersons: Int = 1,
//    @ColumnInfo(name = "qr_code",
//        typeAffinity = ColumnInfo.BLOB)
//        val qrCode: ByteArray? = null,

    @ColumnInfo(name = "timestamp")
        val timestamp: Long = System.currentTimeMillis(),

)
