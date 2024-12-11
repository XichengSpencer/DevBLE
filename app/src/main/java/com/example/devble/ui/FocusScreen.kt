package com.example.devble.ui

import com.example.devble.viewmodel.FocusViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.devble.data.BleStatus
import androidx.compose.ui.graphics.Color

@Composable
fun FocusScreen(viewModel: FocusViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Main Content at the Center
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                // Focus Score Display
                Text(
                    text = "Current Focus Score: ${uiState.focusScore}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Start/Stop Monitoring Button
                Button(
                    onClick = {
                        if (uiState.isMonitoring) {
                            viewModel.stopMonitoring()
                        } else {
                            viewModel.startMonitoring()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = uiState.bleStatus == BleStatus.Connected // Only enabled if BLE is Connected
                ) {
                    Text(text = if (uiState.isMonitoring) "Stop Monitoring" else "Start Monitoring")
                }
            }

            // BLE Status Section at the Bottom
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                // BLE Status Display with Progress Indicators
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "BLE Status: ${uiState.bleStatus}")

                    when (uiState.bleStatus) {
                        BleStatus.Scanning, BleStatus.Connecting -> {
                            Spacer(modifier = Modifier.width(8.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = when (uiState.bleStatus) {
                                    BleStatus.Scanning -> Color.Blue
                                    BleStatus.Connecting -> Color.Green
                                    else -> Color.Gray
                                }
                            )
                        }

                        else -> {
                            // No indicator for Connected or Disconnected
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Connect/Disconnect BLE Button
                Button(
                    onClick = {
                        viewModel.onBleConnectClicked()
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = when (uiState.bleStatus) {
                            BleStatus.Disconnected -> "Connect BLE"
                            else -> "Disconnect BLE"
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Show permission status (for demonstration)
                Text(
                    text = "Bluetooth Permission: ${if (uiState.hasBluetoothPermission) "Granted" else "Not Granted"}"
                )
            }
        }
    }
    }