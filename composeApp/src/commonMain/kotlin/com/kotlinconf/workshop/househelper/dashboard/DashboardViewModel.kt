package com.kotlinconf.workshop.househelper.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinconf.workshop.househelper.Area
import com.kotlinconf.workshop.househelper.data.HouseService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    houseService: HouseService,
) : ViewModel() {
    val rooms: StateFlow<List<Area>> = houseService.getAreas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
