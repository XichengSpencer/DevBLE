package com.example.devble.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


object BleSimulation {
    private val _bleStatus = MutableStateFlow(BleStatus.Disconnected)
    val bleStatus = _bleStatus.asStateFlow()

    fun setBleStatus(status: BleStatus) {
        _bleStatus.value = status
    }
}