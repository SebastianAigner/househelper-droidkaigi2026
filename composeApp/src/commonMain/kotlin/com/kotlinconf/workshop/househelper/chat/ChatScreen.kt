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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

private const val DEVICES_POLL_INTERVAL_MS = 1000L

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel(),
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val isSending by viewModel.isSending.collectAsStateWithLifecycle()
    val serverAddress by viewModel.serverAddress.collectAsStateWithLifecycle()
    val devices by viewModel.devices.collectAsStateWithLifecycle()
    val devicesError by viewModel.devicesError.collectAsStateWithLifecycle()
    val isSavingPower by viewModel.isSavingPower.collectAsStateWithLifecycle()
    var input by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size, isSending) {
        val lastIndex = messages.size - if (isSending) 0 else 1
        if (lastIndex >= 0) listState.animateScrollToItem(lastIndex)
    }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.refreshDevices()
            delay(DEVICES_POLL_INTERVAL_MS)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AddressBar(
            address = serverAddress,
            onSetAddress = viewModel::setServerAddress,
            showAndroidEmulatorNote = viewModel.isAndroid,
        )

        DevicesRow(devices = devices)
        devicesError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            )
        }

        val isResettingHouse by viewModel.isResettingHouse.collectAsStateWithLifecycle()
        val currentWatts = remember(devices) { devices.sumOf { it.watts }.roundToInt() }
        PowerSaveCard(
            currentWatts = currentWatts,
            isSaving = isSavingPower,
            isResetting = isResettingHouse,
            onSavePower = viewModel::savePower,
            onResetHouse = viewModel::resetHouse,
        )

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
private fun PowerSaveCard(
    currentWatts: Int,
    isSaving: Boolean,
    isResetting: Boolean,
    onSavePower: (targetWatts: Int, userAtHome: Boolean) -> Unit,
    onResetHouse: () -> Unit,
) {
    var targetWattsInput by rememberSaveable { mutableStateOf("500") }
    var userAtHome by rememberSaveable { mutableStateOf(true) }
    val targetWatts = targetWattsInput.toIntOrNull()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Power save", style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "Current draw: $currentWatts W",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    value = targetWattsInput,
                    onValueChange = { targetWattsInput = it.filter(Char::isDigit) },
                    modifier = Modifier.weight(1f),
                    label = { Text("Target watts") },
                    singleLine = true,
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("At home", style = MaterialTheme.typography.labelSmall)
                    Switch(checked = userAtHome, onCheckedChange = { userAtHome = it })
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    onClick = { targetWatts?.let { onSavePower(it, userAtHome) } },
                    enabled = !isSaving && targetWatts != null,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(if (isSaving) "Saving power…" else "Save power")
                }
                OutlinedButton(
                    onClick = onResetHouse,
                    enabled = !isResetting,
                ) {
                    Text("Reset house")
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    if (message.isSystem) {
        SystemBubble(message.text)
        return
    }

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

@Composable
private fun SystemBubble(text: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            )
        }
    }
}
