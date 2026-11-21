package com.krishnalabs.roboticsai.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
fun VoiceSettingsScreen(
    currentSettings: RobotSettings,
    prefsRepo: UserPreferencesRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var voiceEnabled by remember { mutableStateOf(currentSettings.voiceEnabled) }
    var speechRate by remember { mutableFloatStateOf(currentSettings.speechRate) }
    var selectedVoice by remember { mutableStateOf(currentSettings.voiceName) }

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
                Text("Voice Synthesis Engine", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        prefsRepo.updateVoiceSettings(voiceEnabled, selectedVoice, speechRate)
                        onBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkPrimaryContainer),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = DarkOnPrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Voice Settings", color = DarkOnPrimary)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Text-to-Speech Output", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text("Enable spoken responses during dialogues", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    }
                    Switch(
                        checked = voiceEnabled,
                        onCheckedChange = { voiceEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = DarkPrimary, checkedTrackColor = DarkPrimaryContainer)
                    )
                }

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Speaking Speed Rate", color = DarkOnSurface, fontSize = 13.sp)
                        Text("${"%.1f".format(speechRate)}x", color = DarkOnSurfaceVariant, fontSize = 13.sp)
                    }
                    Slider(
                        value = speechRate,
                        onValueChange = { speechRate = it },
                        valueRange = 0.5f..2.0f,
                        colors = SliderDefaults.colors(thumbColor = DarkPrimary, activeTrackColor = DarkPrimaryContainer)
                    )
                }
            }
        }
    }
}
