package com.krishnalabs.roboticsai.robotics

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class HardwareConnectionState(val label: String) {
    DISCONNECTED("Offline"),
    CONNECTING("Connecting..."),
    CONNECTED("Connected"),
    NOT_CONFIGURED("Not configured"),
    UNAVAILABLE("Hardware Unavailable")
}

data class ExternalRoboticsState(
    val connectionState: HardwareConnectionState = HardwareConnectionState.NOT_CONFIGURED,
    val bridgeEndpoint: String = "ws://192.168.1.100:9090",
    val raspberryPiStatus: HardwareConnectionState = HardwareConnectionState.UNAVAILABLE,
    val ros2CoreStatus: HardwareConnectionState = HardwareConnectionState.NOT_CONFIGURED,
    val motorControllersStatus: HardwareConnectionState = HardwareConnectionState.UNAVAILABLE,
    val lidarSensorStatus: HardwareConnectionState = HardwareConnectionState.UNAVAILABLE,
    val lastTelemetryReceived: String = "No external packets received"
)

class ExternalRoboticsBridge {

    private val _roboticsState = MutableStateFlow(ExternalRoboticsState())
    val roboticsState: StateFlow<ExternalRoboticsState> = _roboticsState.asStateFlow()

    fun testConnection(endpoint: String) {
        _roboticsState.value = _roboticsState.value.copy(
            bridgeEndpoint = endpoint,
            connectionState = HardwareConnectionState.CONNECTING
        )
        // External hardware check stub (honest reflection of tablet standalone state)
        _roboticsState.value = _roboticsState.value.copy(
            connectionState = HardwareConnectionState.UNAVAILABLE,
            lastTelemetryReceived = "Connection failed: host unreachable or hardware bridge offline"
        )
    }

    fun setEndpoint(endpoint: String) {
        _roboticsState.value = _roboticsState.value.copy(bridgeEndpoint = endpoint)
    }
}
