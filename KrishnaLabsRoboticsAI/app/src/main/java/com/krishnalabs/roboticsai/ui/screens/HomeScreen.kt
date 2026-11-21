package com.krishnalabs.roboticsai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.data.model.RobotSettings
import com.krishnalabs.roboticsai.face.FaceExpression
import com.krishnalabs.roboticsai.face.FaceStateController
import com.krishnalabs.roboticsai.face.RobotFaceCanvas
import com.krishnalabs.roboticsai.hardware.TabletHardwareStatus
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkOutlineVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest
import com.krishnalabs.roboticsai.ui.theme.SemanticSuccess

@Composable
fun HomeScreen(
    settings: RobotSettings,
    hardwareStatus: TabletHardwareStatus,
    faceController: FaceStateController,
    onNavigate: (String) -> Unit
) {
    val faceGeometry by faceController.faceGeometry.collectAsState()
    val isBlinking by faceController.isBlinking.collectAsState()
    val currentExpression by faceController.currentExpression.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Top Row: Robot Identity Banner & Face Mini Preview
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Robot Identity Banner (60% width)
                Card(
                    modifier = Modifier.weight(0.6f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(SemanticSuccess)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = settings.companionMode,
                                    color = SemanticSuccess,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Text(
                                text = "ID: ${settings.robotName}",
                                color = DarkOnSurfaceVariant,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = settings.greetingMessage,
                            color = DarkOnSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(
                                onClick = { onNavigate("assistant") },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkPrimaryContainer),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = DarkOnPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Start Conversation", color = DarkOnPrimary, fontSize = 13.sp)
                            }
                            Button(
                                onClick = { onNavigate("full_screen_face") },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Fullscreen,
                                    contentDescription = null,
                                    tint = DarkOnSurface,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Full-Screen Face", color = DarkOnSurface, fontSize = 13.sp)
                            }
                        }
                    }
                }

                // Interactive Mini Robot Face Card (40% width)
                Card(
                    modifier = Modifier
                        .weight(0.4f)
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLowest)
                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                        RobotFaceCanvas(
                            faceGeometry = faceGeometry,
                            isBlinking = isBlinking,
                            modifier = Modifier.fillMaxSize(),
                            drawHeadOutline = true
                        )
                        Text(
                            text = currentExpression.displayName,
                            color = DarkPrimary,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .clip(RoundedCornerShape(6.dp))
                                .background(DarkSurfaceContainerHigh.copy(alpha = 0.8f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Quick Actions & System Status Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Quick Actions (2x2 Grid)
                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Quick Actions",
                        color = DarkOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ActionCard(
                            title = "Open Camera",
                            subtitle = "Vision feed",
                            icon = Icons.Default.CameraAlt,
                            modifier = Modifier.weight(1f)
                        ) { onNavigate("vision") }

                        ActionCard(
                            title = "Talk to Aria",
                            subtitle = "Voice chat",
                            icon = Icons.Default.Mic,
                            modifier = Modifier.weight(1f)
                        ) { onNavigate("assistant") }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ActionCard(
                            title = "Robot Face",
                            subtitle = "Expressions",
                            icon = Icons.Default.Fullscreen,
                            modifier = Modifier.weight(1f)
                        ) { onNavigate("face") }

                        ActionCard(
                            title = "Controls",
                            subtitle = "Hardware state",
                            icon = Icons.Default.Tune,
                            modifier = Modifier.weight(1f)
                        ) { onNavigate("controls") }
                    }
                }

                // System Status Card
                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "System Status",
                        color = DarkOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            StatusRow(label = "Network", value = hardwareStatus.networkType, icon = Icons.Default.Wifi, isOnline = hardwareStatus.isNetworkConnected)
                            StatusRow(label = "Battery", value = "${hardwareStatus.batteryLevel}%", icon = Icons.Default.BatteryFull, isOnline = true)
                            StatusRow(label = "Volume", value = "${hardwareStatus.volumePercent}%", icon = Icons.Default.VolumeUp, isOnline = true)
                            StatusRow(label = "Vision Camera", value = "Online", icon = Icons.Default.CameraAlt, isOnline = hardwareStatus.isCameraAvailable)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(84.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkSurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = DarkPrimary, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, color = DarkOnSurfaceVariant, fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun StatusRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isOnline: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = label, color = DarkOnSurfaceVariant, fontSize = 12.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (isOnline) SemanticSuccess else Color.Red)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = value, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}
