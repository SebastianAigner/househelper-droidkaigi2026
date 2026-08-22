package com.kotlinconf.workshop.househelper.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

private const val LIGHTS_POLL_INTERVAL_MS = 1000L

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel(),
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val isSending by viewModel.isSending.collectAsStateWithLifecycle()
    val serverAddress by viewModel.serverAddress.collectAsStateWithLifecycle()
    val lights by viewModel.lights.collectAsStateWithLifecycle()
    val lightsError by viewModel.lightsError.collectAsStateWithLifecycle()
    var input by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size, isSending) {
        val lastIndex = messages.size - if (isSending) 0 else 1
        if (lastIndex >= 0) listState.animateScrollToItem(lastIndex)
    }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.refreshLights()
            delay(LIGHTS_POLL_INTERVAL_MS)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AddressBar(
            address = serverAddress,
            onSetAddress = viewModel::setServerAddress,
            showAndroidEmulatorNote = viewModel.isAndroid,
        )

        LightsRow(lights = lights)
        lightsError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            )
        }

        HorizontalDivider()

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(messages) { message ->
                ChatBubble(message)
            }
            if (isSending) {
                item { ChatBubble(ChatMessage("…", isFromUser = false)) }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask your home assistant…") },
            )
            IconButton(
                onClick = {
                    viewModel.sendMessage(input)
                    input = ""
                },
                enabled = input.isNotBlank() && !isSending,
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
private fun AddressBar(
    address: String,
    onSetAddress: (String) -> Unit,
    showAndroidEmulatorNote: Boolean,
) {
    var input by rememberSaveable(address) { mutableStateOf(address) }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                label = { Text("Server address") },
                placeholder = { Text("localhost:8090") },
                singleLine = true,
            )
            Button(onClick = { onSetAddress(input) }) {
                Text("Set")
            }
        }
        if (showAndroidEmulatorNote) {
            Text(
                text = "Use 10.0.2.2 if on Android emulator",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isFromUser) Alignment.End else Alignment.Start
    val containerColor = if (message.isFromUser) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (message.isFromUser) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = containerColor,
            contentColor = contentColor,
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
    }
}
