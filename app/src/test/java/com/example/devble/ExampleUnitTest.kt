package com.example.devble

import com.example.devble.data.BleSimulation
import com.example.devble.data.BleStatus
import com.example.devble.viewmodel.FocusViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class ExampleUnitTest {

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        // Initialize a TestDispatcher and TestScope
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)

        // Set the Main dispatcher to the TestDispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Reset the Main dispatcher after tests
        Dispatchers.resetMain()
        testScope.cancel() // Clean up coroutines
    }

    /**
     * Test to verify the initial state of the FocusViewModel.
     * Ensures that all default values are correctly initialized.
     */
    @Test
    fun `test initial state`() = testScope.runTest {
        val viewModel = FocusViewModel()
        val initialState = viewModel.uiState.value

        assertEquals(0, initialState.focusScore)
        assertFalse(initialState.isMonitoring)
        assertEquals(BleStatus.Disconnected, initialState.bleStatus)
        assertFalse(initialState.hasBluetoothPermission)
    }

    /**
     * Test to ensure that starting monitoring updates the ViewModel state correctly.
     * Verifies BLE connection and monitoring status transitions.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test start monitoring`() = testScope.runTest {
        val viewModel = FocusViewModel()

        // Simulate BLE connection
        viewModel.onBleConnectClicked()
        BleSimulation.setBleStatus(BleStatus.Connected)

        advanceTimeBy(5000)

        viewModel.startMonitoring()
        val updatedState = viewModel.uiState.value
        advanceTimeBy(1000)
        assertTrue(updatedState.isMonitoring)
        assertEquals(BleStatus.Connected, updatedState.bleStatus)
        viewModel.stopMonitoring()
    }

    /**
     * Test to ensure that stopping monitoring resets the focus score and updates the monitoring state.
     */
    @Test
    fun `test stop monitoring resets score`() = testScope.runTest {
        val viewModel = FocusViewModel()

        // Simulate BLE connection and start monitoring
        viewModel.onBleConnectClicked()
        BleSimulation.setBleStatus(BleStatus.Connected)
        advanceTimeBy(5000)

        viewModel.startMonitoring()
        advanceTimeBy(1000)

        // Stop monitoring
        viewModel.stopMonitoring()
        val updatedState = viewModel.uiState.value

        assertFalse(updatedState.isMonitoring)
        assertEquals(0, updatedState.focusScore) // Ensure score resets
        viewModel.disconnectBle()
    }

    /**
     * Test to ensure that disconnecting BLE stops monitoring and resets the focus score and BLE status.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test BLE disconnect stops monitoring`() = testScope.runTest {
        val viewModel = FocusViewModel()

        // Simulate BLE connection and start monitoring
        viewModel.onBleConnectClicked()
        BleSimulation.setBleStatus(BleStatus.Connected)
        advanceTimeBy(5000)

        viewModel.startMonitoring()
        advanceTimeBy(1000)
        // Simulate BLE disconnection
        viewModel.disconnectBle()
        advanceTimeBy(100)
        val updatedState = viewModel.uiState.value

        assertFalse(updatedState.isMonitoring)
        assertEquals(0, updatedState.focusScore)
        assertEquals(BleStatus.Disconnected, updatedState.bleStatus)
    }
}