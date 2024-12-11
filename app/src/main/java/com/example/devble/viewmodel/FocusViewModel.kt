package com.example.devble.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devble.data.BleSimulation
import com.example.devble.data.BleStatus
import com.example.devble.data.FocusRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val focusScore: Int = 0,
    val isMonitoring: Boolean = false,
    val bleStatus: BleStatus = BleStatus.Disconnected,
    val hasBluetoothPermission: Boolean = false
)

class FocusViewModel : ViewModel() {
    private val repository = FocusRepository()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var monitoringJob: Job? = null
    private var bleConnectionJob: Job? = null
    private var bleStatusObserverJob: Job? = null

    init {
        // Observe BLE connection state
        bleStatusObserverJob = viewModelScope.launch {
            BleSimulation.bleStatus.collect { status ->
                _uiState.update { it.copy(bleStatus = status) }

                // If BLE is disconnected, stop monitoring
                if (status == BleStatus.Disconnected) {
                    stopMonitoring()
                }
            }
        }
    }

    fun startMonitoring() {
        if (_uiState.value.isMonitoring || _uiState.value.bleStatus != BleStatus.Connected) return
        _uiState.update { it.copy(isMonitoring = true) }

        monitoringJob = viewModelScope.launch {
            repository.getFocusScores().collect { score ->
                _uiState.update { it.copy(focusScore = score) }
            }
        }
    }

    fun stopMonitoring() {
        if (!_uiState.value.isMonitoring) return
        monitoringJob?.cancel()
        _uiState.update { it.copy(isMonitoring = false, focusScore = 0) }
    }

    fun onBleConnectClicked() {
        // If we don't have permission, simulate requesting it
        if (!_uiState.value.hasBluetoothPermission) {
            requestBluetoothPermission()
        } else {
            // If we have permission, initiate BLE connection flow
            if (_uiState.value.bleStatus == BleStatus.Disconnected) {
                initiateBleConnection()
            } else {
                disconnectBle()
            }
        }
    }

    private fun requestBluetoothPermission() {
        // Simulate permission request outcome
        // In a real app, you'd trigger a permission dialog and update state based on the result.
        val permissionGranted = true // Simulated

        if (permissionGranted) {
            _uiState.update { it.copy(hasBluetoothPermission = true) }
            initiateBleConnection()
        } else {
            // If permission denied, do not connect
            _uiState.update { it.copy(hasBluetoothPermission = false) }
        }
    }

    private fun initiateBleConnection() {
        bleConnectionJob?.cancel()
        bleConnectionJob = viewModelScope.launch {
            BleSimulation.setBleStatus(BleStatus.Scanning)
            delay(2000) // Simulate Scanning for 2 seconds

            BleSimulation.setBleStatus(BleStatus.Connecting)
            delay(2000) // Simulate Connecting for 2 seconds

            BleSimulation.setBleStatus(BleStatus.Connected)
        }
    }

    fun disconnectBle() {
        bleConnectionJob?.cancel()
        BleSimulation.setBleStatus(BleStatus.Disconnected)
    }

    override fun onCleared() {
        super.onCleared()

        // Cancel all ongoing jobs
        monitoringJob?.cancel()
        bleConnectionJob?.cancel()
        bleStatusObserverJob?.cancel()
    }
}