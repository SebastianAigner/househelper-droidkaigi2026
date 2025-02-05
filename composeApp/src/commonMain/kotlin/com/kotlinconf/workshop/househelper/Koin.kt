package com.kotlinconf.workshop.househelper

import com.kotlinconf.workshop.househelper.dashboard.AreaViewModel
import com.kotlinconf.workshop.househelper.dashboard.DashboardViewModel
import com.kotlinconf.workshop.househelper.dashboard.FavoritesViewModel
import com.kotlinconf.workshop.househelper.data.HouseService
import com.kotlinconf.workshop.househelper.database.AppDatabase
import com.kotlinconf.workshop.househelper.database.DatabaseHouseService
import com.kotlinconf.workshop.househelper.database.DatabaseInitializer
import com.kotlinconf.workshop.househelper.database.DatabaseProvider
import com.kotlinconf.workshop.househelper.database.getRoomDatabase
import com.kotlinconf.workshop.househelper.devices.CameraDetailsViewModel
import com.kotlinconf.workshop.househelper.devices.LightDetailsViewModel
import com.kotlinconf.workshop.househelper.devices.RenameDeviceViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module


fun createKoinConfig(
    databaseProvider: DatabaseProvider,
) = koinConfiguration {
    val appModule = module {
        single<AppDatabase> { 
            val database = getRoomDatabase(databaseProvider.createDatabaseBuilder())
            val initializer = DatabaseInitializer(database)
            initializer.initializeDatabase()
            database
        }
        single<HouseService> { DatabaseHouseService(get()) }

        // Using demo service for now
//        single<HouseService> { DatabaseHouseService(get()) }
    }

    val viewModelModule = module {
        viewModelOf(::CameraDetailsViewModel)
        viewModelOf(::DashboardViewModel)
        viewModelOf(::LightDetailsViewModel)
        viewModelOf(::RenameDeviceViewModel)
        viewModelOf(::AreaViewModel)
        viewModelOf(::FavoritesViewModel)
    }

    modules(appModule, viewModelModule)
}
