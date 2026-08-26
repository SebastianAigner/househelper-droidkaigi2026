package com.kotlinconf.workshop.househelper.navigation

import androidx.navigation3.runtime.NavKey
import com.kotlinconf.workshop.househelper.DeviceId
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey

// Start screens
@Serializable
data object OnboardingWelcome : Screen

@Serializable
data object OnboardingAbout : Screen

@Serializable
data object OnboardingDone : Screen

// Main screens
@Serializable
data object Dashboard : Screen

@Serializable
data class LightDetails(val deviceId: DeviceId) : Screen

@Serializable
data class CameraDetails(val deviceId: DeviceId) : Screen

// TODO add rename screen
