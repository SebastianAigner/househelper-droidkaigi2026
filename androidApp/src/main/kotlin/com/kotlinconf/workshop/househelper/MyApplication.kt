package com.kotlinconf.workshop.househelper

import android.app.Application
import com.kotlinconf.workshop.househelper.database.AndroidDatabaseProvider
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(createKoinConfig(AndroidDatabaseProvider(this),))
    }
}
