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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.data.model.ActivityEvent
import com.krishnalabs.roboticsai.data.model.EventCategory
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.SemanticError
import com.krishnalabs.roboticsai.ui.theme.SemanticSuccess
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ActivityScreen() {
    var selectedCategory by remember { mutableStateOf(EventCategory.ALL) }

    val sampleEvents = remember {
        listOf(
            ActivityEvent(
                title = "Camera Vision Frame Captured",
                category = EventCategory.CAMERA,
                description = "Multimodal scene inspection triggered by user."
            ),
            ActivityEvent(
                title = "Speech Synthesis Completed",
                category = EventCategory.VOICE,
                description = "Aria spoke response utterance successfully."
            ),
            ActivityEvent(
                title = "Gemini AI Connection Active",
                category = EventCategory.AI_CONNECTION,
                description = "Latency 340ms to cloud model endpoint."
            ),
            ActivityEvent(
                title = "Battery Telemetry Nominal",
                category = EventCategory.HARDWARE,
                description = "Current charge level at 98%, thermal parameters regular."
            ),
            ActivityEvent(
                title = "External ROS2 Bridge Offline",
                category = EventCategory.ERRORS,
                description = "WebSocket endpoint ws://192.168.1.100:9090 unreachable.",
                isSuccess = false
            )
        )
    }

    val filteredEvents = remember(selectedCategory) {
        if (selectedCategory == EventCategory.ALL) sampleEvents
        else sampleEvents.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text("Activity & Telemetry Log", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text("System operations, AI dialogues, and hardware events", color = DarkOnSurfaceVariant, fontSize = 12.sp)
        }

        // Category Filter Chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(EventCategory.values()) { category ->
                val isSelected = category == selectedCategory
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) DarkPrimaryContainer else DarkSurfaceContainerLow)
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = category.name.replace("_", " "),
                        color = if (isSelected) DarkOnPrimary else DarkOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        // Event List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredEvents) { event ->
                val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(event.timestamp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(if (event.isSuccess) DarkPrimaryContainer.copy(alpha = 0.25f) else SemanticError.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (event.isSuccess) Icons.Default.CheckCircle else Icons.Default.ErrorOutline,
                                contentDescription = null,
                                tint = if (event.isSuccess) DarkPrimary else SemanticError,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = event.title, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = timeStr, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = event.description, color = DarkOnSurfaceVariant, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
