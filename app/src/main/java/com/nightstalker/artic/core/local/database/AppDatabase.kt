package com.nightstalker.artic.core.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nightstalker.artic.core.local.ticket.LocalTicket
import com.nightstalker.artic.core.local.ticket.TicketDao

@Database(  entities = [LocalTicket::class], version = 1, exportSchema = false,  )
//@TypeConverters( DataConversion::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val tickets: TicketDao
    abstract fun getTicketsDao(): TicketDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private const val databaseName = "artic.db"
        const val ARTIC_TABLE = "ARTIC_TABLE"

        fun getInstance(context: Context): AppDatabase {
            Log.d("Artic-Database","Step #1 Artic-Database was created")
            if (instance == null) {
                synchronized(this) {
                    Log.d(ARTIC_TABLE,"Start!!! Artic-Database")
                    instance = createDatabaseInstance(context)
                }
            }
            Log.d("Artic-Database","Step #2 Artic-Database was created")
            return instance!!
        }

//        fun getInstance(context: Context): AppDatabase {
//            synchronized(this) {
//                return instance ?: instance ?: createDatabaseInstance(context)
//            }
//        }

//        private fun migrationList() = arrayOf(
//            object : Migration(1, 2) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL("ALTER TABLE ticket  ADD COLUMN img_id TEXT NOT NULL DEFAULT ''")
//                }
//            },
//        )

        private fun createDatabaseInstance(context: Context): AppDatabase {
            return Room
                .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    databaseName
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }



}