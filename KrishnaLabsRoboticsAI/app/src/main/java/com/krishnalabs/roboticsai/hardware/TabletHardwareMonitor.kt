package com.krishnalabs.roboticsai.hardware

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.BatteryManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TabletHardwareStatus(
    val batteryLevel: Int = 100,
    val isCharging: Boolean = false,
    val isNetworkConnected: Boolean = true,
    val networkType: String = "Wi-Fi",
    val volumePercent: Int = 75,
    val isMicrophoneAvailable: Boolean = true,
    val isCameraAvailable: Boolean = true
)

class TabletHardwareMonitor(private val context: Context) {

    private val _status = MutableStateFlow(TabletHardwareStatus())
    val status: StateFlow<TabletHardwareStatus> = _status.asStateFlow()

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL

            val batteryPct = if (level >= 0 && scale > 0) (level * 100 / scale) else 100
            _status.value = _status.value.copy(
                batteryLevel = batteryPct,
                isCharging = isCharging
            )
        }
    }

    fun startMonitoring() {
        // Battery filter
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, filter)

        // Read audio level
        updateVolume()

        // Read network
        updateNetwork()
    }

    fun updateVolume() {
        audioManager?.let { am ->
            val maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC)
            val pct = if (maxVol > 0) (currentVol * 100 / maxVol) else 75
            _status.value = _status.value.copy(volumePercent = pct)
        }
    }

    private fun updateNetwork() {
        connectivityManager?.let { cm ->
            val activeNetwork: Network? = cm.activeNetwork
            val caps: NetworkCapabilities? = cm.getNetworkCapabilities(activeNetwork)
            val isConnected = caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            val type = when {
                caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "Wi-Fi"
                caps?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Cellular"
                caps?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> "Ethernet"
                else -> "Disconnected"
            }
            _status.value = _status.value.copy(
                isNetworkConnected = isConnected,
                networkType = type
            )
        }
    }

    fun setSystemVolume(percent: Int) {
        audioManager?.let { am ->
            val maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val target = (percent * maxVol / 100).coerceIn(0, maxVol)
            am.setStreamVolume(AudioManager.STREAM_MUSIC, target, 0)
            updateVolume()
        }
    }

    fun stopMonitoring() {
        try {
            context.unregisterReceiver(batteryReceiver)
        } catch (_: Exception) {}
    }
}
