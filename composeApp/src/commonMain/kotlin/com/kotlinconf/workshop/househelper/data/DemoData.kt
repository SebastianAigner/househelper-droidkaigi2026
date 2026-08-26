package com.kotlinconf.workshop.househelper.data

import com.kotlinconf.workshop.househelper.Area

import com.kotlinconf.workshop.househelper.CameraDevice
import com.kotlinconf.workshop.househelper.DeviceConstants
import com.kotlinconf.workshop.househelper.DeviceId

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
        deviceId = DeviceId("living_room_main_light"),
        name = "Main Light",
        areaId = "living_room",
        isOn = true,
        brightness = 85,
        color = DeviceConstants.Light.PREDEFINED_COLORS[2].color
    ),
    LightDevice(
        deviceId = DeviceId("living_room_floor_lamp"),
        name = "Floor Lamp",
        areaId = "living_room",
        brightness = 0
    ),
    LightDevice(
        deviceId = DeviceId("living_room_reading_light"),
        name = "Reading Light",
        areaId = "living_room",
        isOn = true,
        brightness = 65,
        color = DeviceConstants.Light.PREDEFINED_COLORS[3].color
    ),
    SwitchDevice(
        deviceId = DeviceId("living_room_tv_switch"),
        name = "TV Switch",
        areaId = "living_room",
        isOn = true
    ),
    SwitchDevice(
        deviceId = DeviceId("living_room_gaming_console"),
        name = "Gaming Console",
        areaId = "living_room"
    ),
    ThermostatDevice(
        deviceId = DeviceId("living_room_temperature"),
        name = "Temperature",
        areaId = "living_room"
    ),
    HumidityDevice(
        deviceId = DeviceId("living_room_humidity"),
        name = "Humidity",
        areaId = "living_room"
    ),
    CameraDevice(
        deviceId = DeviceId("living_room_security_camera"),
        name = "Security Camera",
        areaId = "living_room",
        isOn = true
    ),

    // Kitchen devices
    LightDevice(
        deviceId = DeviceId("kitchen_ceiling_light"),
        name = "Ceiling Light",
        areaId = "kitchen",
        brightness = 0
    ),
    LightDevice(
        deviceId = DeviceId("kitchen_counter_light"),
        name = "Counter Light",
        areaId = "kitchen",
        isOn = true,
        brightness = 90,
        color = DeviceConstants.Light.PREDEFINED_COLORS[5].color
    ),
    LightDevice(
        deviceId = DeviceId("kitchen_under_cabinet_light"),
        name = "Under Cabinet Light",
        areaId = "kitchen",
        isOn = true,
        brightness = 75
    ),
    HumidityDevice(deviceId = DeviceId("kitchen_humidity"), name = "Humidity", areaId = "kitchen"),
    SwitchDevice(deviceId = DeviceId("kitchen_oven_switch"), name = "Oven Switch", areaId = "kitchen"),
    SwitchDevice(
        deviceId = DeviceId("kitchen_dishwasher"),
        name = "Dishwasher",
        areaId = "kitchen",
        isOn = true
    ),
    ThermostatDevice(
        deviceId = DeviceId("kitchen_temperature"),
        name = "Temperature",
        areaId = "kitchen"
    ),
    CameraDevice(
        deviceId = DeviceId("kitchen_security_camera"),
        name = "Security Camera",
        areaId = "kitchen"
    ),

    // Bathroom devices
    LightDevice(
        deviceId = DeviceId("bathroom_main_light"),
        name = "Main Light",
        areaId = "bathroom",
        isOn = true,
        brightness = 95
    ),
    LightDevice(
        deviceId = DeviceId("bathroom_mirror_light"),
        name = "Mirror Light",
        areaId = "bathroom",
        isOn = true,
        brightness = 80
    ),
    LightDevice(
        deviceId = DeviceId("bathroom_shower_light"),
        name = "Shower Light",
        areaId = "bathroom",
        brightness = 0
    ),
    HumidityDevice(deviceId = DeviceId("bathroom_humidity"), name = "Humidity", areaId = "bathroom"),
    ThermostatDevice(
        deviceId = DeviceId("bathroom_temperature"),
        name = "Temperature",
        areaId = "bathroom"
    ),
    CameraDevice(
        deviceId = DeviceId("bathroom_security_camera"),
        name = "Security Camera",
        areaId = "bathroom"
    ),

    // Bedroom devices
    LightDevice(
        deviceId = DeviceId("bedroom_main_light"),
        name = "Main Light",
        areaId = "bedroom",
        brightness = 0
    ),
    LightDevice(
        deviceId = DeviceId("bedroom_bedside_lamp_left"),
        name = "Bedside Lamp Left",
        areaId = "bedroom",
        isOn = true,
        brightness = 45
    ),
    LightDevice(
        deviceId = DeviceId("bedroom_bedside_lamp_right"),
        name = "Bedside Lamp Right",
        areaId = "bedroom",
        brightness = 0
    ),
    SwitchDevice(deviceId = DeviceId("bedroom_tv_switch"), name = "TV Switch", areaId = "bedroom"),
    SwitchDevice(
        deviceId = DeviceId("bedroom_air_purifier"),
        name = "Air Purifier",
        areaId = "bedroom",
        isOn = true
    ),
    ThermostatDevice(
        deviceId = DeviceId("bedroom_temperature"),
        name = "Temperature",
        areaId = "bedroom"
    ),
    HumidityDevice(deviceId = DeviceId("bedroom_humidity"), name = "Humidity", areaId = "bedroom"),
    CameraDevice(
        deviceId = DeviceId("bedroom_security_camera"),
        name = "Security Camera",
        areaId = "bedroom"
    )
)
