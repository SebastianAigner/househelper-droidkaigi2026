package com.kotlinconf.workshop.househelper.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class AndroidDatabaseProvider(private val context: Context) : DatabaseProvider {
    override fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(AppDatabase.DATABASE_NAME)
        return Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = dbFile.absolutePath
        )
    }
}
