package com.kotlinconf.workshop.househelper.chat

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Shared across ChatService and DevicesService so both hit the same Koog agent backend.
class ConnectionSettings(
    val isAndroid: Boolean = false,
) {
    private val _serverAddress = MutableStateFlow(DEFAULT_ADDRESS)
    val serverAddress: StateFlow<String> = _serverAddress.asStateFlow()

    fun setServerAddress(address: String) {
        _serverAddress.value = address.trim().ifEmpty { DEFAULT_ADDRESS }
    }

    fun baseUrl(): String = "http://${serverAddress.value}"

    companion object {
        const val DEFAULT_ADDRESS = "localhost:8090"
    }
}
