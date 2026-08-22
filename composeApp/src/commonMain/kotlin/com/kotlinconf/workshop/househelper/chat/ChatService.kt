package com.kotlinconf.workshop.househelper.chat

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
private data class ChatRequest(val message: String)

@Serializable
private data class ChatResponse(val response: String)

class ChatService(
    private val httpClient: HttpClient,
    private val connectionSettings: ConnectionSettings,
) {
    suspend fun sendMessage(message: String): String {
        val response = httpClient.post("${connectionSettings.baseUrl()}/api/chat") {
            contentType(ContentType.Application.Json)
            setBody(ChatRequest(message))
        }
        return response.body<ChatResponse>().response
    }
}
