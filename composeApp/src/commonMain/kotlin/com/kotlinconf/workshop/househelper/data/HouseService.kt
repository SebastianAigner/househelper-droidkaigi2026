package com.kotlinconf.workshop.househelper.data

import androidx.compose.ui.graphics.Color
import com.kotlinconf.workshop.househelper.Area
import com.kotlinconf.workshop.househelper.CameraDevice
import com.kotlinconf.workshop.househelper.Device
import com.kotlinconf.workshop.househelper.DeviceId
import kotlinx.coroutines.flow.Flow

interface HouseService {
    fun getAreas(): Flow<List<Area>>
    fun getDevicesForArea(areaId: String): Flow<List<Device>>
    fun getDevice(deviceId: DeviceId): Flow<Device?>
    fun getCamera(deviceId: DeviceId): Flow<CameraDevice?>
    fun getCameraFootage(deviceId: DeviceId): Flow<String>
    suspend fun toggle(deviceId: DeviceId): Boolean
    suspend fun setBrightness(deviceId: DeviceId, brightness: Int)
    suspend fun setColor(deviceId: DeviceId, color: Color)
    suspend fun rename(deviceId: DeviceId, name: String)
    suspend fun toggleFavorite(deviceId: DeviceId): Boolean
    fun getFavoriteDevices(): Flow<List<Device>>
}
