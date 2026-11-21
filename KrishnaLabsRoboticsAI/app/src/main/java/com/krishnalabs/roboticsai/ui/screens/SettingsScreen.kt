package com.krishnalabs.roboticsai.ui.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.navigation.Screen
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow

data class SettingsCategory(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val targetRoute: String
)

@Composable
fun SettingsScreen(onNavigate: (String) -> Unit) {
    val categories = listOf(
        SettingsCategory("Robot Identity", "Name, companion mode, persona instructions", Icons.Default.SmartToy, Screen.RobotIdentitySettings.route),
        SettingsCategory("Gemini AI", "Model version, secure API keys, parameters", Icons.Default.Psychology, Screen.GeminiAiSettings.route),
        SettingsCategory("Voice Synthesis", "TTS profiles, pitch, speaking speed rate", Icons.Default.Mic, Screen.VoiceSettings.route),
        SettingsCategory("Robot Face Customization", "Iris colors, blink rates, expression timing", Icons.Default.Face, Screen.FaceCustomizationSettings.route),
        SettingsCategory("Camera & Vision", "Lens facing, flashlight, resolution limits", Icons.Default.Videocam, Screen.CameraSettings.route),
        SettingsCategory("Appearance & Themes", "Dark theme, typography styles, contrast", Icons.Default.ColorLens, Screen.AppearanceSettings.route),
        SettingsCategory("Privacy & Permissions", "Hardware runtime permissions & storage", Icons.Default.Lock, Screen.PrivacyPermissionsSettings.route),
        SettingsCategory("About Application", "Krishna Labs version 1.0.0, license & diagnostics", Icons.Default.Info, Screen.AboutSettings.route)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text("System Settings", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text("Configure robotic persona, AI intelligence, and tablet hardware", color = DarkOnSurfaceVariant, fontSize = 12.sp)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(item.targetRoute) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = item.icon, contentDescription = null, tint = DarkPrimary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.title, color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = item.description, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}
