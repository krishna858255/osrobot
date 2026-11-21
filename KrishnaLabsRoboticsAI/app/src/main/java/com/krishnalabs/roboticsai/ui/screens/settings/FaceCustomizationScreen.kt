package com.krishnalabs.roboticsai.ui.screens.settings

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.data.model.RobotSettings
import com.krishnalabs.roboticsai.data.preferences.UserPreferencesRepository
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import kotlinx.coroutines.launch

@Composable
fun FaceCustomizationScreen(
    currentSettings: RobotSettings,
    prefsRepo: UserPreferencesRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedColorHex by remember { mutableStateOf(currentSettings.eyeColorHex) }
    var animSpeed by remember { mutableFloatStateOf(currentSettings.animationSpeed) }

    val colors = listOf(
        "#ADC6FF" to "Cyan Blue",
        "#34D399" to "Emerald",
        "#FFB786" to "Amber",
        "#F472B6" to "Neon Pink",
        "#A78BFA" to "Deep Violet"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkOnSurface)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Robot Face Customization", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        prefsRepo.updateFaceSettings(selectedColorHex, animSpeed)
                        onBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkPrimaryContainer),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = DarkOnPrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Face Tuning", color = DarkOnPrimary)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                Column {
                    Text("Iris Luminescence Color", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        colors.forEach { (hex, name) ->
                            val color = Color(android.graphics.Color.parseColor(hex))
                            val isSelected = selectedColorHex.equals(hex, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color)
                                    .clickable { selectedColorHex = hex }
                            )
                        }
                    }
                }

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Animation Interpolation Speed", color = DarkOnSurface, fontSize = 13.sp)
                        Text("${"%.1f".format(animSpeed)}x", color = DarkOnSurfaceVariant, fontSize = 13.sp)
                    }
                    Slider(
                        value = animSpeed,
                        onValueChange = { animSpeed = it },
                        valueRange = 0.5f..2.5f,
                        colors = SliderDefaults.colors(thumbColor = DarkPrimary, activeTrackColor = DarkPrimaryContainer)
                    )
                }
            }
        }
    }
}
