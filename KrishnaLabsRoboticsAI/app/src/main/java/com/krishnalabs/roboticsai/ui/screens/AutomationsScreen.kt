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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.data.model.AutomationRule
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow

@Composable
fun AutomationsScreen() {
    val automations = remember {
        mutableStateListOf(
            AutomationRule(
                id = "1",
                name = "Morning Greeting Routine",
                triggerDescription = "Trigger: 08:00 AM daily",
                actionDescription = "Action: Wake face, set expression to Happy, speak briefing",
                isEnabled = true,
                iconName = "WbSunny"
            ),
            AutomationRule(
                id = "2",
                name = "Low Battery Standby",
                triggerDescription = "Trigger: Battery drops below 15%",
                actionDescription = "Action: Switch to Sleepy expression, dim screen, notify user",
                isEnabled = true,
                iconName = "BatteryChargingFull"
            ),
            AutomationRule(
                id = "3",
                name = "Night Time Power Save",
                triggerDescription = "Trigger: 11:00 PM or idle > 30 mins",
                actionDescription = "Action: Turn off camera, close eye animations, mute audio",
                isEnabled = false,
                iconName = "Bedtime"
            ),
            AutomationRule(
                id = "4",
                name = "Face Recognition Alert",
                triggerDescription = "Trigger: Human face identified via CameraX",
                actionDescription = "Action: Turn eyes to Curious, play soft chime, initiate greeting",
                isEnabled = true,
                iconName = "Face"
            ),
            AutomationRule(
                id = "5",
                name = "Motion Anomaly Detection",
                triggerDescription = "Trigger: Sudden movement in camera frame",
                actionDescription = "Action: Log event in Activity feed and capture snapshot",
                isEnabled = false,
                iconName = "Visibility"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Robotic Automations", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("Autonomous background behaviors and scheduled responses", color = DarkOnSurfaceVariant, fontSize = 12.sp)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(automations) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = if (item.isEnabled) DarkPrimary else DarkOnSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = item.name,
                                    color = DarkOnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Switch(
                                checked = item.isEnabled,
                                onCheckedChange = { isChecked ->
                                    val idx = automations.indexOfFirst { it.id == item.id }
                                    if (idx != -1) {
                                        automations[idx] = automations[idx].copy(isEnabled = isChecked)
                                    }
                                },
                                colors = SwitchDefaults.colors(checkedThumbColor = DarkPrimary, checkedTrackColor = DarkPrimaryContainer)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = item.triggerDescription, color = DarkOnSurfaceVariant, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = item.actionDescription, color = DarkOnSurface, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
