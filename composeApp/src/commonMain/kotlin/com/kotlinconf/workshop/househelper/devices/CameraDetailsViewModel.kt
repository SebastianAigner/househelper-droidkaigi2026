package com.kotlinconf.workshop.househelper.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinconf.workshop.househelper.CameraDevice
import com.kotlinconf.workshop.househelper.data.HouseService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CameraDetailsViewModel(
    private val houseService: HouseService,
    private val deviceId: String,
) : ViewModel() {
    // TODO implement ViewModel here
}
