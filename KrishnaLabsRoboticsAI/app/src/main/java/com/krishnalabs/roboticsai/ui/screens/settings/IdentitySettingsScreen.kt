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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.krishnalabs.roboticsai.ui.theme.DarkOutlineVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest
import kotlinx.coroutines.launch

@Composable
fun IdentitySettingsScreen(
    currentSettings: RobotSettings,
    prefsRepo: UserPreferencesRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var robotName by remember { mutableStateOf(currentSettings.robotName) }
    var assistantName by remember { mutableStateOf(currentSettings.assistantName) }
    var personalityPrompt by remember { mutableStateOf(currentSettings.personalityPrompt) }
    var greetingMessage by remember { mutableStateOf(currentSettings.greetingMessage) }

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
                Text("Robot Identity & Persona", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        prefsRepo.updateRobotIdentity(robotName, assistantName, personalityPrompt, greetingMessage)
                        onBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkPrimaryContainer),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = DarkOnPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Changes", color = DarkOnPrimary)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                OutlinedTextField(
                    value = robotName,
                    onValueChange = { robotName = it },
                    label = { Text("Robot Hardware Serial / Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceContainerLowest,
                        unfocusedContainerColor = DarkSurfaceContainerLowest,
                        focusedBorderColor = DarkPrimary,
                        unfocusedBorderColor = DarkOutlineVariant,
                        focusedTextColor = DarkOnSurface,
                        unfocusedTextColor = DarkOnSurface
                    )
                )

                OutlinedTextField(
                    value = assistantName,
                    onValueChange = { assistantName = it },
                    label = { Text("Assistant Persona Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceContainerLowest,
                        unfocusedContainerColor = DarkSurfaceContainerLowest,
                        focusedBorderColor = DarkPrimary,
                        unfocusedBorderColor = DarkOutlineVariant,
                        focusedTextColor = DarkOnSurface,
                        unfocusedTextColor = DarkOnSurface
                    )
                )

                OutlinedTextField(
                    value = greetingMessage,
                    onValueChange = { greetingMessage = it },
                    label = { Text("Default Greeting Speech") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceContainerLowest,
                        unfocusedContainerColor = DarkSurfaceContainerLowest,
                        focusedBorderColor = DarkPrimary,
                        unfocusedBorderColor = DarkOutlineVariant,
                        focusedTextColor = DarkOnSurface,
                        unfocusedTextColor = DarkOnSurface
                    )
                )

                OutlinedTextField(
                    value = personalityPrompt,
                    onValueChange = { personalityPrompt = it },
                    label = { Text("System Personality Directive") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceContainerLowest,
                        unfocusedContainerColor = DarkSurfaceContainerLowest,
                        focusedBorderColor = DarkPrimary,
                        unfocusedBorderColor = DarkOutlineVariant,
                        focusedTextColor = DarkOnSurface,
                        unfocusedTextColor = DarkOnSurface
                    )
                )
            }
        }
    }
}
