package com.krishnalabs.roboticsai.ui.screens.settings

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkOnSurface)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("About Krishna Labs Robotics AI", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.PrecisionManufacturing, contentDescription = null, tint = DarkPrimary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Krishna Labs Robotics AI", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Text("Version 1.0.0 (Release Build 2026.11)", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "A complete native Android tablet application acting as the primary computing brain for autonomous robotics companions. Built with Jetpack Compose, CameraX, Gemini Multimodal AI, and custom vector animated face engine.",
                    color = DarkOnSurfaceVariant,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text("Architect & Engineer: Krishna Mohite", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text("UI/UX System: Google Stitch Design Reference", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                Text("Platform: Android Tablet Landscape (1280x800)", color = DarkOnSurfaceVariant, fontSize = 12.sp)
            }
        }
    }
}
