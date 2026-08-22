package com.kotlinconf.workshop.househelper.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
)

class ChatViewModel(
    private val chatService: ChatService,
    private val lightsService: LightsService,
    private val connectionSettings: ConnectionSettings,
) : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    val serverAddress: StateFlow<String> = connectionSettings.serverAddress
    val isAndroid: Boolean = connectionSettings.isAndroid

    private val _lights = MutableStateFlow<List<AssistantLight>>(emptyList())
    val lights: StateFlow<List<AssistantLight>> = _lights.asStateFlow()

    private val _lightsError = MutableStateFlow<String?>(null)
    val lightsError: StateFlow<String?> = _lightsError.asStateFlow()

    fun setServerAddress(address: String) {
        connectionSettings.setServerAddress(address)
    }

    // Driven by a LaunchedEffect scoped to the Assistant tab's composition, so
    // polling stops as soon as the tab is left instead of running forever in the background.
    suspend fun refreshLights() {
        try {
            _lights.value = lightsService.getLights()
            _lightsError.value = null
        } catch (e: Exception) {
            _lightsError.value = "Couldn't load lights: ${e.message}"
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank() || _isSending.value) return

        _messages.update { it + ChatMessage(text, isFromUser = true) }
        _isSending.value = true

        viewModelScope.launch {
            val reply = try {
                chatService.sendMessage(text)
            } catch (e: Exception) {
                "Sorry, I couldn't reach the assistant: ${e.message}"
            }
            _messages.update { it + ChatMessage(reply, isFromUser = false) }
            _isSending.value = false
        }
    }
}
