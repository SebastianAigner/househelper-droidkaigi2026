package com.kotlinconf.workshop.househelper.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * iOS implementation of the DatabaseProvider interface.
 */
@OptIn(ExperimentalForeignApi::class)
class IosDatabaseProvider : DatabaseProvider {
    override fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = documentDirectory() + "/" + AppDatabase.DATABASE_NAME
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}
