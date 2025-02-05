package com.kotlinconf.workshop.househelper.data

import com.kotlinconf.workshop.househelper.Area

import com.kotlinconf.workshop.househelper.CameraDevice
import com.kotlinconf.workshop.househelper.DeviceConstants

import com.kotlinconf.workshop.househelper.HumidityDevice
import com.kotlinconf.workshop.househelper.LightDevice
import com.kotlinconf.workshop.househelper.SwitchDevice
import com.kotlinconf.workshop.househelper.ThermostatDevice

val demoAreas = listOf(
    Area(id = "living_room", name = "Living Area"),
    Area(id = "kitchen", name = "Kitchen"),
    Area(id = "bathroom", name = "Bathroom"),
    Area(id = "bedroom", name = "Bedroom")
)

val demoDevices = listOf(
    // Living Room devices
    LightDevice(
        deviceId = "living_room_main_light",
        name = "Main Light",
        areaId = "living_room",
        isOn = true,
        brightness = 85,
        color = DeviceConstants.Light.PREDEFINED_COLORS[2].color
    ),
    LightDevice(
        deviceId = "living_room_floor_lamp",
        name = "Floor Lamp",
        areaId = "living_room",
        brightness = 0
    ),
    LightDevice(
        deviceId = "living_room_reading_light",
        name = "Reading Light",
        areaId = "living_room",
        isOn = true,
        brightness = 65,
        color = DeviceConstants.Light.PREDEFINED_COLORS[3].color
    ),
    SwitchDevice(
        deviceId = "living_room_tv_switch",
        name = "TV Switch",
        areaId = "living_room",
        isOn = true
    ),
    SwitchDevice(
        deviceId = "living_room_gaming_console",
        name = "Gaming Console",
        areaId = "living_room"
    ),
    ThermostatDevice(
        deviceId = "living_room_temperature",
        name = "Temperature",
        areaId = "living_room"
    ),
    HumidityDevice(
        deviceId = "living_room_humidity",
        name = "Humidity",
        areaId = "living_room"
    ),
    CameraDevice(
        deviceId = "living_room_security_camera",
        name = "Security Camera",
        areaId = "living_room",
        isOn = true
    ),

    // Kitchen devices
    LightDevice(
        deviceId = "kitchen_ceiling_light",
        name = "Ceiling Light",
        areaId = "kitchen",
        brightness = 0
    ),
    LightDevice(
        deviceId = "kitchen_counter_light",
        name = "Counter Light",
        areaId = "kitchen",
        isOn = true,
        brightness = 90,
        color = DeviceConstants.Light.PREDEFINED_COLORS[5].color
    ),
    LightDevice(
        deviceId = "kitchen_under_cabinet_light",
        name = "Under Cabinet Light",
        areaId = "kitchen",
        isOn = true,
        brightness = 75
    ),
    HumidityDevice(deviceId = "kitchen_humidity", name = "Humidity", areaId = "kitchen"),
    SwitchDevice(deviceId = "kitchen_oven_switch", name = "Oven Switch", areaId = "kitchen"),
    SwitchDevice(
        deviceId = "kitchen_dishwasher",
        name = "Dishwasher",
        areaId = "kitchen",
        isOn = true
    ),
    ThermostatDevice(
        deviceId = "kitchen_temperature",
        name = "Temperature",
        areaId = "kitchen"
    ),
    CameraDevice(
        deviceId = "kitchen_security_camera",
        name = "Security Camera",
        areaId = "kitchen"
    ),

    // Bathroom devices
    LightDevice(
        deviceId = "bathroom_main_light",
        name = "Main Light",
        areaId = "bathroom",
        isOn = true,
        brightness = 95
    ),
    LightDevice(
        deviceId = "bathroom_mirror_light",
        name = "Mirror Light",
        areaId = "bathroom",
        isOn = true,
        brightness = 80
    ),
    LightDevice(
        deviceId = "bathroom_shower_light",
        name = "Shower Light",
        areaId = "bathroom",
        brightness = 0
    ),
    HumidityDevice(deviceId = "bathroom_humidity", name = "Humidity", areaId = "bathroom"),
    ThermostatDevice(
        deviceId = "bathroom_temperature",
        name = "Temperature",
        areaId = "bathroom"
    ),
    CameraDevice(
        deviceId = "bathroom_security_camera",
        name = "Security Camera",
        areaId = "bathroom"
    ),

    // Bedroom devices
    LightDevice(
        deviceId = "bedroom_main_light",
        name = "Main Light",
        areaId = "bedroom",
        brightness = 0
    ),
    LightDevice(
        deviceId = "bedroom_bedside_lamp_left",
        name = "Bedside Lamp Left",
        areaId = "bedroom",
        isOn = true,
        brightness = 45
    ),
    LightDevice(
        deviceId = "bedroom_bedside_lamp_right",
        name = "Bedside Lamp Right",
        areaId = "bedroom",
        brightness = 0
    ),
    SwitchDevice(deviceId = "bedroom_tv_switch", name = "TV Switch", areaId = "bedroom"),
    SwitchDevice(
        deviceId = "bedroom_air_purifier",
        name = "Air Purifier",
        areaId = "bedroom",
        isOn = true
    ),
    ThermostatDevice(
        deviceId = "bedroom_temperature",
        name = "Temperature",
        areaId = "bedroom"
    ),
    HumidityDevice(deviceId = "bedroom_humidity", name = "Humidity", areaId = "bedroom"),
    CameraDevice(
        deviceId = "bedroom_security_camera",
        name = "Security Camera",
        areaId = "bedroom"
    )
)
