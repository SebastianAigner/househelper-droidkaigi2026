package com.kotlinconf.workshop.househelper.data

import androidx.compose.ui.graphics.Color
import com.kotlinconf.workshop.househelper.Area
import com.kotlinconf.workshop.househelper.CameraDevice
import com.kotlinconf.workshop.househelper.Device
import kotlinx.coroutines.flow.Flow

interface HouseService {
    fun getAreas(): Flow<List<Area>>
    fun getDevicesForArea(areaId: String): Flow<List<Device>>
    fun getDevice(deviceId: String): Flow<Device?>
    fun getCamera(deviceId: String): Flow<CameraDevice?>
    fun getCameraFootage(deviceId: String): Flow<String>
    suspend fun toggle(deviceId: String): Boolean
    suspend fun setBrightness(deviceId: String, brightness: Int)
    suspend fun setColor(deviceId: String, color: Color)
    suspend fun rename(deviceId: String, name: String)
    suspend fun toggleFavorite(deviceId: String): Boolean
    fun getFavoriteDevices(): Flow<List<Device>>
}
