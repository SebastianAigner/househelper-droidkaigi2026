package com.kotlinconf.workshop.househelper.database

import com.kotlinconf.workshop.househelper.data.demoAreas
import com.kotlinconf.workshop.househelper.data.demoDevices
import com.kotlinconf.workshop.househelper.database.entities.AreaEntity
import com.kotlinconf.workshop.househelper.database.entities.DeviceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DatabaseInitializer(private val database: AppDatabase) {
    
    private val scope = CoroutineScope(Dispatchers.Default)
    
    fun initializeDatabase() {
        scope.launch {
            val roomCount = database.roomDao().getAllAreas().first().size
            if (roomCount == 0) {
                // Database is empty, populate with demo data
                populateDemoData()
            }
        }
    }
    
    private suspend fun populateDemoData() {
        // Insert rooms
        val roomEntities = demoAreas.map { AreaEntity.fromArea(it) }
        database.roomDao().insertAreas(roomEntities)
        
        // Insert devices
        val deviceEntities = demoDevices.map { DeviceEntity.fromDevice(it) }
        database.deviceDao().insertDevices(deviceEntities)
    }
}
