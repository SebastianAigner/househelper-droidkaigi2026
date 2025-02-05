package com.kotlinconf.workshop.househelper.database

import androidx.room.RoomDatabase

/**
 * Platform-specific database provider interface.
 * Each platform will have its own implementation of this interface.
 */
interface DatabaseProvider {
    /**
     * Creates and returns a Room database builder.
     */
    fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}
