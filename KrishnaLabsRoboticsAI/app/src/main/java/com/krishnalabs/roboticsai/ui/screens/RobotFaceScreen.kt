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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest

@Composable
fun RobotFaceScreen(
    settings: RobotSettings,
    faceController: FaceStateController,
    onOpenFullScreen: () -> Unit
) {
    val faceGeometry by faceController.faceGeometry.collectAsState()
    val isBlinking by faceController.isBlinking.collectAsState()
    val currentExpression by faceController.currentExpression.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Left Column: Interactive Face Canvas (65% width)
        Card(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLowest)
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                RobotFaceCanvas(
                    faceGeometry = faceGeometry,
                    isBlinking = isBlinking,
                    modifier = Modifier.fillMaxSize(),
                    drawHeadOutline = true
                )

                // Immersive Fullscreen Button
                Button(
                    onClick = onOpenFullScreen,
                    modifier = Modifier.align(Alignment.TopEnd),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Fullscreen, contentDescription = null, tint = DarkOnSurface)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Immersive Mode", color = DarkOnSurface, fontSize = 12.sp)
                }
            }
        }

        // Right Column: Expression Switcher & Tuning (35% width)
        Column(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Expression Presets", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(12.dp))

                    val expressions = FaceExpression.values()
                    // 2-column grid of expression pills
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in expressions.indices step 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ExpressionPill(
                                    expression = expressions[i],
                                    isSelected = currentExpression == expressions[i],
                                    modifier = Modifier.weight(1f)
                                ) { faceController.setExpression(expressions[i]) }

                                if (i + 1 < expressions.size) {
                                    ExpressionPill(
                                        expression = expressions[i + 1],
                                        isSelected = currentExpression == expressions[i + 1],
                                        modifier = Modifier.weight(1f)
                                    ) { faceController.setExpression(expressions[i + 1]) }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Iris Glow Palette", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(12.dp))

                    val colorOptions = listOf(
                        Color(0xFFADC6FF), // Cyan/Blue
                        Color(0xFF34D399), // Emerald
                        Color(0xFFFFB786), // Amber
                        Color(0xFFF472B6), // Pink
                        Color(0xFFA78BFA)  // Violet
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        colorOptions.forEach { col ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(col)
                                    .clickable { faceController.setEyeColor(col) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpressionPill(
    expression: FaceExpression,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) DarkPrimaryContainer else DarkSurfaceContainerHigh)
            .clickable { onClick() }
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = expression.displayName,
            color = if (isSelected) DarkOnPrimary else DarkOnSurface,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
