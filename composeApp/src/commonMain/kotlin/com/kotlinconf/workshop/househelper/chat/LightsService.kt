package com.kotlinconf.workshop.househelper.chat

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

@Serializable
data class AssistantLightColor(
    val red: Int,
    val green: Int,
    val blue: Int,
)

@Serializable
data class AssistantLight(
    val id: String,
    val name: String,
    val color: AssistantLightColor,
    val brightness: Int,
    val on: Boolean,
)

class LightsService(
    private val httpClient: HttpClient,
    private val connectionSettings: ConnectionSettings,
) {
    suspend fun getLights(): List<AssistantLight> =
        httpClient.get("${connectionSettings.baseUrl()}/api/lights").body()
}
