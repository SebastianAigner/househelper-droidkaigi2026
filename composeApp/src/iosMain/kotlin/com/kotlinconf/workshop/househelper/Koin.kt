package com.kotlinconf.workshop.househelper

import com.kotlinconf.workshop.househelper.database.IosDatabaseProvider
import org.koin.core.context.startKoin

fun startKoin() {
    startKoin(createKoinConfig(IosDatabaseProvider()))
}
