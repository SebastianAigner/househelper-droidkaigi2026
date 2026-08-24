package com.kotlinconf.workshop.househelper.chat

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
private data class PowerSaveRequest(
    val targetWatts: Int,
    val userAtHome: Boolean,
)

@Serializable
data class PowerSaveResult(
    val success: Boolean,
    val targetWatts: Int,
    val currentWatts: Int,
    val retries: Int,
    val response: String,
)

class PowerSaveService(
    private val httpClient: HttpClient,
    private val connectionSettings: ConnectionSettings,
) {
    suspend fun savePower(targetWatts: Int, userAtHome: Boolean): PowerSaveResult {
        val response = httpClient.post("${connectionSettings.baseUrl()}/api/power-save") {
            contentType(ContentType.Application.Json)
            setBody(PowerSaveRequest(targetWatts, userAtHome))
        }
        return response.body()
    }
}
