package com.kotlinconf.workshop.househelper.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * Desktop implementation of the DatabaseProvider interface.
 */
class DesktopDatabaseProvider : DatabaseProvider {
    override fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), AppDatabase.DATABASE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath
        )
    }
}
