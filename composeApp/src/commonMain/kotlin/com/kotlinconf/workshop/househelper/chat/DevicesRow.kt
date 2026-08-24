package com.kotlinconf.workshop.househelper.chat

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

private val OnFlashColor = Color(0xFF43A047)
private val OffFlashColor = Color(0xFFE53935)

@Composable
fun DevicesRow(devices: List<AssistantDevice>) {
    if (devices.isEmpty()) return

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        items(devices, key = { it.id }) { device ->
            DeviceChip(device)
        }
    }
}

@Composable
private fun DeviceChip(device: AssistantDevice) {
    var isFirstUpdate by remember { mutableStateOf(true) }
    var flashOn by remember { mutableStateOf<Boolean?>(null) }
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(device.on) {
        if (isFirstUpdate) {
            isFirstUpdate = false
            return@LaunchedEffect
        }

        flashOn = device.on
        rotation.snapTo(0f)
        rotation.animateTo(-8f, tween(60))
        rotation.animateTo(8f, tween(100))
        rotation.animateTo(-5f, tween(100))
        rotation.animateTo(0f, tween(80))
        delay(500)
        flashOn = null
    }

    val borderColor by animateColorAsState(
        targetValue = when (flashOn) {
            true -> OnFlashColor
            false -> OffFlashColor
            null -> Color.Transparent
        },
        animationSpec = tween(if (flashOn == null) 700 else 80),
    )

    val indicatorColor = when {
        device.kind == "light" && device.on && device.color != null ->
            Color(device.color.red, device.color.green, device.color.blue)

        device.on -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val subtitle = when {
        !device.on -> "Off"
        device.kind == "light" -> "${device.brightness}%"
        else -> "${device.watts.roundToInt()} W"
    }

    Surface(
        modifier = Modifier
            .rotate(rotation.value)
            .border(2.dp, borderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Surface(
                modifier = Modifier.size(24.dp).clip(CircleShape),
                color = indicatorColor,
            ) {}
            Text(device.name, style = MaterialTheme.typography.labelMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
