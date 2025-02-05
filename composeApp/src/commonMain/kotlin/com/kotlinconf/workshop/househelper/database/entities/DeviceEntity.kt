package com.kotlinconf.workshop.househelper.database.entities

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlinconf.workshop.househelper.CameraDevice
import com.kotlinconf.workshop.househelper.Device
import com.kotlinconf.workshop.househelper.HumidityDevice
import com.kotlinconf.workshop.househelper.LightDevice
import com.kotlinconf.workshop.househelper.SwitchDevice
import com.kotlinconf.workshop.househelper.ThermostatDevice

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val areaId: String,
    val type: DeviceType,
    val isOn: Boolean = false,
    val brightness: Int? = null,
    val colorRed: Float? = null,
    val colorGreen: Float? = null,
    val colorBlue: Float? = null,
    val colorAlpha: Float? = null,
    val currentValue: Float? = null,
    val isFavorite: Boolean = false
) {
    fun toDevice(): Device {
        val deviceId = id
        val areaIdObj = areaId

        return when (type) {
            DeviceType.LIGHT -> LightDevice(
                deviceId = deviceId,
                name = name,
                areaId = areaIdObj,
                isOn = isOn,
                isFavorite = isFavorite,
                brightness = brightness ?: 50,
                color = if (colorRed != null && colorGreen != null && colorBlue != null && colorAlpha != null) {
                    Color(colorRed, colorGreen, colorBlue, colorAlpha)
                } else {
                    Color.White
                }
            )
            DeviceType.SWITCH -> SwitchDevice(
                deviceId = deviceId,
                name = name,
                areaId = areaIdObj,
                isOn = isOn,
                isFavorite = isFavorite
            )
            DeviceType.HUMIDITY -> HumidityDevice(
                deviceId = deviceId,
                name = name,
                areaId = areaIdObj,
                isFavorite = isFavorite,
                currentValue = currentValue ?: 50f
            )
            DeviceType.THERMOSTAT -> ThermostatDevice(
                deviceId = deviceId,
                name = name,
                areaId = areaIdObj,
                isFavorite = isFavorite,
                currentValue = currentValue ?: 20f
            )
            DeviceType.CAMERA -> CameraDevice(
                deviceId = deviceId,
                name = name,
                areaId = areaIdObj,
                isOn = isOn,
                isFavorite = isFavorite
            )
        }
    }

    companion object {
        fun fromDevice(device: Device): DeviceEntity {
            return when (device) {
                is LightDevice -> DeviceEntity(
                    id = device.deviceId,
                    name = device.name,
                    areaId = device.areaId,
                    type = DeviceType.LIGHT,
                    isOn = device.isOn,
                    brightness = device.brightness,
                    colorRed = device.color.red,
                    colorGreen = device.color.green,
                    colorBlue = device.color.blue,
                    colorAlpha = device.color.alpha,
                    isFavorite = device.isFavorite
                )
                is SwitchDevice -> DeviceEntity(
                    id = device.deviceId,
                    name = device.name,
                    areaId = device.areaId,
                    type = DeviceType.SWITCH,
                    isOn = device.isOn,
                    isFavorite = device.isFavorite
                )
                is HumidityDevice -> DeviceEntity(
                    id = device.deviceId,
                    name = device.name,
                    areaId = device.areaId,
                    type = DeviceType.HUMIDITY,
                    currentValue = device.currentValue,
                    isFavorite = device.isFavorite
                )
                is ThermostatDevice -> DeviceEntity(
                    id = device.deviceId,
                    name = device.name,
                    areaId = device.areaId,
                    type = DeviceType.THERMOSTAT,
                    currentValue = device.currentValue,
                    isFavorite = device.isFavorite
                )
                is CameraDevice -> DeviceEntity(
                    id = device.deviceId,
                    name = device.name,
                    areaId = device.areaId,
                    type = DeviceType.CAMERA,
                    isOn = device.isOn,
                    isFavorite = device.isFavorite
                )
                else -> throw IllegalArgumentException("Unknown device type: ${device::class.simpleName}")
            }
        }
    }
}

enum class DeviceType {
    LIGHT,
    SWITCH,
    HUMIDITY,
    THERMOSTAT,
    CAMERA
}
