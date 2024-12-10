## Table of Contents
- [Features](#features)
- [Approach](#approach)
- [Prerequisites](#prerequisites)
- [Build and Run](#build-and-run)

## Features
- Real-Time Focus Score: Displays a simulated focus score that updates every 5 seconds.
- Start/Stop Monitoring: Allows users to start or stop the focus monitoring process.
- BLE Connection Simulation: Simulates the BLE connection flow with states: Scanning, Connecting, and Connected.
- Asynchronous Operations: Utilizes Kotlin Coroutines to handle background tasks without blocking the UI.
- Responsive UI: Built with Jetpack Compose for a modern and reactive user interface.
- State Preservation: Maintains UI state across configuration changes like screen rotations.

## Approach

### 1. Architecture and State Management
- **MVVM Pattern**: Implemented the Model-View-ViewModel (MVVM) architecture to separate concerns, ensuring a clear distinction between UI components and business logic.
- **ViewModel**: Used `FocusViewModel` to manage UI state and handle business logic, ensuring state persistence across configuration changes.
- **StateFlow**: Leveraged Kotlin's `StateFlow` for reactive and observable state management, allowing the UI to automatically update in response to state changes.

### 2. User Interface with Jetpack Compose
- **Composable Functions**: Designed the UI using Jetpack Compose's declarative paradigm, creating reusable and modular UI components.
- **Dynamic UI Elements**: Included buttons to start/stop monitoring and connect/disconnect BLE, as well as text displays for focus scores and BLE statuses.
- **Progress Indicators**: Added visual indicators to represent BLE scanning and connecting phases, enhancing user feedback.

### 3. Asynchronous Operations with Kotlin Coroutines
- **Focus Score Simulation**: Implemented a `FocusRepository` that emits random focus scores every 5 seconds using Kotlin Coroutines and `Flow`.
- **BLE Connection Flow**: Simulated the BLE connection process through different states (`Scanning`, `Connecting`, `Connected`) with timed delays to mimic real-world behavior.
- **Lifecycle Awareness**: Managed coroutine scopes within the `ViewModel` to ensure tasks are appropriately canceled during lifecycle changes, preventing memory leaks.

### 4. BLE Simulation and Permission Handling
- **State Management**: Created a `BleSimulation` object to manage and emit BLE statuses, allowing the UI to react to state changes.
- **Permission Simulation**: Simulated Bluetooth permission requests within the `ViewModel`, toggling BLE connection states based on permission outcomes.

### 5. Code Quality and Modularity
- **Clean Code Practices**: Structured the codebase into distinct packages (`ui`, `data`, `viewmodel`, etc.) to promote readability and maintainability.
- **Separation of Concerns**: Ensured that data handling, business logic, and UI rendering are handled independently, facilitating easier testing and scalability.

## Prerequisites
- **Android Studio**: [Download and install](https://developer.android.com/studio) the latest version of Android Studio.
- **Android SDK**: Ensure that the Android SDK is installed and up to date.
- **Kotlin**: The project uses Kotlin; Android Studio typically includes the necessary Kotlin support.

## Build and Run

### Option 1: Run Through Android Studio


1. Open in Android Studio:
    - Launch Android Studio.
    - Click on "Open an existing project".
    - Navigate to the cloned repository folder and select it.

2. Build the Project:
    - Allow Android Studio to sync and build the project. The IDE will download the necessary dependencies and set up the environment automatically.

3. Run the App:
    - Connect an Android device via USB or start an Android emulator.
    - Click the "Run" button (▶️) in Android Studio.
    - Select your target device or emulator to deploy and run the app.

4. Interact with the App:
    - **Connect BLE**: Click the "Connect BLE" button to simulate Bluetooth connection. Observe the BLE status transitions: Scanning → Connecting → Connected.
    - **Start Monitoring**: Once BLE is connected, the "Start Monitoring" button becomes enabled. Click it to begin receiving simulated focus scores every 5 seconds.
    - **Stop Monitoring**: Click the "Stop Monitoring" button to halt focus score updates.
    - **Disconnect BLE**: Clicking "Disconnect BLE" will disconnect the simulated Bluetooth connection and automatically stop monitoring if it was active.

### Option 2: Run Through APK

1. Locate APK:
    - Looking for app-debug.apk in the attachment.

2. Transfer the APK to Your Device:
    - Copy the APK file to your Android device via USB or any file transfer method.

3. Install the APK:
    - On your Android device, enable "Install unknown apps" in your device settings if not already enabled.
    - Locate the APK file and install it.

4. Run the App:
    - Open the app from your device’s app drawer and interact with it as described above.
