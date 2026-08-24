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
    val isSystem: Boolean = false,
)

class ChatViewModel(
    private val chatService: ChatService,
    private val devicesService: DevicesService,
    private val powerSaveService: PowerSaveService,
    private val connectionSettings: ConnectionSettings,
) : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    val serverAddress: StateFlow<String> = connectionSettings.serverAddress
    val isAndroid: Boolean = connectionSettings.isAndroid

    private val _devices = MutableStateFlow<List<AssistantDevice>>(emptyList())
    val devices: StateFlow<List<AssistantDevice>> = _devices.asStateFlow()

    private val _devicesError = MutableStateFlow<String?>(null)
    val devicesError: StateFlow<String?> = _devicesError.asStateFlow()

    private val _isSavingPower = MutableStateFlow(false)
    val isSavingPower: StateFlow<Boolean> = _isSavingPower.asStateFlow()

    private val _isResettingHouse = MutableStateFlow(false)
    val isResettingHouse: StateFlow<Boolean> = _isResettingHouse.asStateFlow()

    fun setServerAddress(address: String) {
        connectionSettings.setServerAddress(address)
    }

    // Driven by a LaunchedEffect scoped to the Assistant tab's composition, so
    // polling stops as soon as the tab is left instead of running forever in the background.
    suspend fun refreshDevices() {
        try {
            _devices.value = devicesService.getDevices()
            _devicesError.value = null
        } catch (e: Exception) {
            _devicesError.value = "Couldn't load devices: ${e.message}"
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank() || _isSending.value) return

        _messages.value = listOf(ChatMessage(text, isFromUser = true))
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

    fun savePower(targetWatts: Int, userAtHome: Boolean) {
        if (_isSavingPower.value) return

        _messages.value = listOf(
            ChatMessage(
                "[Running power adjustment routine for $targetWatts W]",
                isFromUser = true,
                isSystem = true,
            )
        )
        _isSavingPower.value = true

        viewModelScope.launch {
            val reply = try {
                val result = powerSaveService.savePower(targetWatts, userAtHome)
                val summary = if (result.success) {
                    "[Reached the target: now drawing ${result.currentWatts} W (target ${result.targetWatts} W)]"
                } else {
                    "[Couldn't reach the target after ${result.retries} retries: still drawing " +
                        "${result.currentWatts} W (target ${result.targetWatts} W)]"
                }
                listOf(summary, result.response).filter(String::isNotBlank).joinToString(separator = "\n\n")
            } catch (e: Exception) {
                "Sorry, I couldn't run power-save: ${e.message}"
            }
            _messages.update { it + ChatMessage(reply, isFromUser = false) }
            _isSavingPower.value = false
        }
    }

    fun resetHouse() {
        if (_isResettingHouse.value) return

        _isResettingHouse.value = true
        viewModelScope.launch {
            try {
                _devices.value = devicesService.resetDevices()
                _devicesError.value = null
            } catch (e: Exception) {
                _devicesError.value = "Couldn't reset the house: ${e.message}"
            }
            _isResettingHouse.value = false
        }
    }
}
