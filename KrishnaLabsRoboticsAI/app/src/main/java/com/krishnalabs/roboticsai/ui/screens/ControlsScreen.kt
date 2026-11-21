package com.krishnalabs.roboticsai.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CastConnected
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SettingsInputAntenna
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.hardware.TabletHardwareMonitor
import com.krishnalabs.roboticsai.robotics.ExternalRoboticsBridge
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.SemanticWarning

@Composable
fun ControlsScreen(
    hardwareMonitor: TabletHardwareMonitor,
    roboticsBridge: ExternalRoboticsBridge
) {
    val hardwareStatus by hardwareMonitor.status.collectAsState()
    val roboticsState by roboticsBridge.roboticsState.collectAsState()

    var volumeSlider by remember(hardwareStatus.volumePercent) {
        mutableFloatStateOf(hardwareStatus.volumePercent.toFloat())
    }

    var cameraEnabled by remember { mutableStateOf(true) }
    var microphoneEnabled by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Left Column: On-Device Tablet Controls (50% width)
        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("On-Device Tablet Controls", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Speaker Volume Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = DarkPrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("System Audio Volume", color = DarkOnSurface, fontSize = 13.sp)
                            }
                            Text("${volumeSlider.toInt()}%", color = DarkOnSurfaceVariant, fontSize = 13.sp)
                        }
                        Slider(
                            value = volumeSlider,
                            onValueChange = {
                                volumeSlider = it
                                hardwareMonitor.setSystemVolume(it.toInt())
                            },
                            valueRange = 0f..100f,
                            colors = SliderDefaults.colors(thumbColor = DarkPrimary, activeTrackColor = DarkPrimaryContainer)
                        )
                    }

                    // Toggles
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Camera System Active", color = DarkOnSurface, fontSize = 13.sp)
                        Switch(
                            checked = cameraEnabled,
                            onCheckedChange = { cameraEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = DarkPrimary, checkedTrackColor = DarkPrimaryContainer)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Microphone Listener Active", color = DarkOnSurface, fontSize = 13.sp)
                        Switch(
                            checked = microphoneEnabled,
                            onCheckedChange = { microphoneEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = DarkPrimary, checkedTrackColor = DarkPrimaryContainer)
                        )
                    }
                }
            }
        }

        // Right Column: External Robotics Hardware Bridge (50% width)
        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("External Robotics Bridge", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.SettingsInputAntenna, contentDescription = null, tint = DarkPrimary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("ROS 2 / rosbridge WebSocket", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text(roboticsState.bridgeEndpoint, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Raspberry Pi Coprocessor", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                        Text(roboticsState.raspberryPiStatus.label, color = SemanticWarning, fontSize = 12.sp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Motor Actuators (CAN)", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                        Text(roboticsState.motorControllersStatus.label, color = SemanticWarning, fontSize = 12.sp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("LiDAR Rangefinder", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                        Text(roboticsState.lidarSensorStatus.label, color = SemanticWarning, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = { roboticsBridge.testConnection(roboticsState.bridgeEndpoint) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh)
                    ) {
                        Icon(imageVector = Icons.Default.CastConnected, contentDescription = null, tint = DarkOnSurface, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ping Hardware Bridge", color = DarkOnSurface, fontSize = 13.sp)
                    }

                    Text(
                        text = "Note: External hardware integration is kept isolated. Tablet functions fully as autonomous brain when external physical bridges are disconnected.",
                        color = DarkOnSurfaceVariant.copy(alpha = 0.6f),
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}
