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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.VpnKey
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.krishnalabs.roboticsai.ui.theme.SemanticSuccess
import kotlinx.coroutines.launch

@Composable
fun GeminiSettingsScreen(
    currentSettings: RobotSettings,
    prefsRepo: UserPreferencesRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedModel by remember { mutableStateOf(currentSettings.geminiModel) }
    var apiKeyInput by remember { mutableStateOf("") }
    var testResult by remember { mutableStateOf<String?>(null) }

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
                Text("Gemini AI Engine Configuration", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        prefsRepo.updateGeminiConfig(selectedModel, apiKeyInput)
                        testResult = "Configuration securely saved to encrypted local preferences"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkPrimaryContainer),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = DarkOnPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Credentials", color = DarkOnPrimary)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = DarkPrimary)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Secure Credential Management", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }

                Text(
                    text = "API keys are never hardcoded in the codebase. Keys are stored via Encrypted DataStore / Android Keystore. For production deployments, Firebase AI Logic with App Check is recommended.",
                    color = DarkOnSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                OutlinedTextField(
                    value = selectedModel,
                    onValueChange = { selectedModel = it },
                    label = { Text("Gemini Model Target") },
                    placeholder = { Text("e.g. gemini-2.0-flash or gemini-1.5-pro") },
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
                    value = apiKeyInput,
                    onValueChange = { apiKeyInput = it },
                    label = { Text("Google AI API Key (Optional Override)") },
                    placeholder = { Text(if (currentSettings.geminiApiKeyConfigured) "•••••••••••••••• (Configured)" else "AIzaSy...") },
                    visualTransformation = PasswordVisualTransformation(),
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

                if (testResult != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SemanticSuccess, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(testResult!!, color = SemanticSuccess, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
