package com.kotlinconf.workshop.househelper

import com.kotlinconf.workshop.househelper.chat.ChatService
import com.kotlinconf.workshop.househelper.chat.ChatViewModel
import com.kotlinconf.workshop.househelper.chat.ConnectionSettings
import com.kotlinconf.workshop.househelper.chat.LightsService
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
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module


fun createKoinConfig(
    databaseProvider: DatabaseProvider,
    isAndroid: Boolean = false,
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

        single<HttpClient> {
            HttpClient {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 120_000
                    connectTimeoutMillis = 15_000
                    socketTimeoutMillis = 120_000
                }
            }
        }
        single { ConnectionSettings(isAndroid) }
        single { ChatService(get(), get()) }
        single { LightsService(get(), get()) }
    }

    val viewModelModule = module {
        viewModelOf(::CameraDetailsViewModel)
        viewModelOf(::DashboardViewModel)
        viewModelOf(::LightDetailsViewModel)
        viewModelOf(::RenameDeviceViewModel)
        viewModelOf(::AreaViewModel)
        viewModelOf(::FavoritesViewModel)
        viewModelOf(::ChatViewModel)
    }

    modules(appModule, viewModelModule)
}
