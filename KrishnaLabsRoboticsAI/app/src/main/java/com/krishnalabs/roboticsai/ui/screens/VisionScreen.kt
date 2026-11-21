package com.krishnalabs.roboticsai.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.ai.AiResponseResult
import com.krishnalabs.roboticsai.ai.GeminiAssistantEngine
import com.krishnalabs.roboticsai.camera.CameraPreview
import com.krishnalabs.roboticsai.camera.CameraViewModel
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest
import kotlinx.coroutines.launch

@Composable
fun VisionScreen(
    cameraViewModel: CameraViewModel,
    aiEngine: GeminiAssistantEngine
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isFlashlightOn by cameraViewModel.isFlashlightOn.collectAsState()
    var visionAnalysisText by remember {
        mutableStateOf("Camera live preview active. Tap 'Inspect Frame' to run Gemini Multimodal Vision reasoning.")
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Left Column: Live CameraX Viewport (68% width)
        Card(
            modifier = Modifier
                .weight(0.68f)
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLowest)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraPreview(
                    cameraViewModel = cameraViewModel,
                    modifier = Modifier.fillMaxSize()
                )

                // Floating Camera Controls Over Viewport
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(DarkSurfaceContainerLowest.copy(alpha = 0.85f))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { cameraViewModel.toggleCameraLens() }) {
                        Icon(imageVector = Icons.Default.Cameraswitch, contentDescription = "Switch Camera", tint = DarkOnSurface)
                    }

                    IconButton(
                        onClick = {
                            cameraViewModel.capturePhoto(context) { file ->
                                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                                if (bitmap != null) {
                                    coroutineScope.launch {
                                        val res = aiEngine.analyzeImage(bitmap, "Analyze robot camera scene")
                                        if (res is AiResponseResult.Success) {
                                            visionAnalysisText = res.reply
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(26.dp))
                            .background(DarkPrimaryContainer)
                    ) {
                        Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "Capture Frame", tint = DarkOnPrimary)
                    }

                    IconButton(onClick = { cameraViewModel.toggleFlashlight() }) {
                        Icon(
                            imageVector = Icons.Default.FlashlightOn,
                            contentDescription = "Flashlight",
                            tint = if (isFlashlightOn) Color.Yellow else DarkOnSurface
                        )
                    }
                }
            }
        }

        // Right Column: Multimodal Vision Diagnostics (32% width)
        Column(
            modifier = Modifier
                .weight(0.32f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Scene Understanding", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = visionAnalysisText,
                        color = DarkOnSurfaceVariant,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Camera Feed Specs", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("API: CameraX 1.4", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    Text("Target FPS: 30 / 60", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    Text("Flashlight: ${if (isFlashlightOn) "Active" else "Off"}", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                }
            }
        }
    }
}
