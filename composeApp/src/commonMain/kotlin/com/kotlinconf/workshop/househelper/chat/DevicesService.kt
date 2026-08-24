package com.kotlinconf.workshop.househelper.chat

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.serialization.Serializable

@Serializable
data class AssistantDeviceColor(
    val red: Int,
    val green: Int,
    val blue: Int,
)

@Serializable
data class AssistantDevice(
    val id: String,
    val name: String,
    val kind: String,
    val on: Boolean,
    val watts: Double,
    val brightness: Int? = null,
    val color: AssistantDeviceColor? = null,
)

class DevicesService(
    private val httpClient: HttpClient,
    private val connectionSettings: ConnectionSettings,
) {
    suspend fun getDevices(): List<AssistantDevice> =
        httpClient.get("${connectionSettings.baseUrl()}/api/devices").body()

    suspend fun resetDevices(): List<AssistantDevice> =
        httpClient.post("${connectionSettings.baseUrl()}/api/devices/reset").body()
}
