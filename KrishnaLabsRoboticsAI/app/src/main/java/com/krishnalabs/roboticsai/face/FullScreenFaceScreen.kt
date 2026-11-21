package com.krishnalabs.roboticsai.face

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest

@Composable
fun FullScreenFaceScreen(
    controller: FaceStateController,
    onExitFullScreen: () -> Unit
) {
    val faceGeometry by controller.faceGeometry.collectAsState()
    val isBlinking by controller.isBlinking.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkSurfaceContainerLowest)
    ) {
        // Immersive Canvas (Takes up virtually whole tablet viewport)
        RobotFaceCanvas(
            faceGeometry = faceGeometry,
            isBlinking = isBlinking,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp, vertical = 32.dp),
            drawHeadOutline = false // In full screen immersive mode, tablet screen is the robot head!
        )

        // Subtle Exit Floating Button in Top-Right
        IconButton(
            onClick = onExitFullScreen,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(DarkSurfaceContainerHigh.copy(alpha = 0.7f))
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Exit Full Screen Face",
                tint = Color.White
            )
        }

        // Bottom status whisper
        Text(
            text = "Tap X to exit immersive companion mode",
            color = DarkOnSurfaceVariant.copy(alpha = 0.5f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}
