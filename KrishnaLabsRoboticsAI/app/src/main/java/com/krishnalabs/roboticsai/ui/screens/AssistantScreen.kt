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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.krishnalabs.roboticsai.ai.AiResponseResult
import com.krishnalabs.roboticsai.ai.GeminiAssistantEngine
import com.krishnalabs.roboticsai.data.model.ConversationMessage
import com.krishnalabs.roboticsai.data.model.MessageSender
import com.krishnalabs.roboticsai.data.model.RobotSettings
import com.krishnalabs.roboticsai.face.FaceExpression
import com.krishnalabs.roboticsai.face.FaceStateController
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkOutlineVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest
import com.krishnalabs.roboticsai.ui.theme.SemanticSuccess
import com.krishnalabs.roboticsai.voice.AudioEngineState
import com.krishnalabs.roboticsai.voice.AudioVoiceEngine
import kotlinx.coroutines.launch

@Composable
fun AssistantScreen(
    settings: RobotSettings,
    faceController: FaceStateController,
    voiceEngine: AudioVoiceEngine?,
    aiEngine: GeminiAssistantEngine
) {
    val coroutineScope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ConversationMessage(
                sender = MessageSender.ROBOT,
                text = "Greetings Krishna! Voice recognition and Gemini reasoning are online. Ask me anything or tap the microphone to speak."
            )
        )
    }

    val voiceState = voiceEngine?.engineState?.collectAsState()?.value ?: AudioEngineState.IDLE

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Left Column: Chat Conversation Thread (70% width)
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxSize()
        ) {
            // Messages Scrollable Feed
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { msg ->
                    ChatBubble(message = msg)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Voice Activity Bar
            if (voiceState != AudioEngineState.IDLE) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkPrimaryContainer.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(SemanticSuccess)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voice Engine: ${voiceState.name}...",
                        color = DarkPrimary,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Input Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Mic Button
                IconButton(
                    onClick = {
                        if (voiceState == AudioEngineState.LISTENING) {
                            voiceEngine?.stopListening()
                            faceController.setExpression(FaceExpression.IDLE)
                        } else {
                            voiceEngine?.startListening()
                            faceController.setExpression(FaceExpression.LISTENING)
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (voiceState == AudioEngineState.LISTENING) Color.Red
                            else DarkSurfaceContainerLow
                        )
                ) {
                    Icon(
                        imageVector = if (voiceState == AudioEngineState.LISTENING) Icons.Default.Stop else Icons.Default.Mic,
                        contentDescription = "Voice Input",
                        tint = if (voiceState == AudioEngineState.LISTENING) Color.White else DarkPrimary
                    )
                }

                // Text Field
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Type message for Aria...", color = DarkOnSurfaceVariant, fontSize = 13.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceContainerLowest,
                        unfocusedContainerColor = DarkSurfaceContainerLowest,
                        focusedBorderColor = DarkPrimary,
                        unfocusedBorderColor = DarkOutlineVariant.copy(alpha = 0.5f),
                        focusedTextColor = DarkOnSurface,
                        unfocusedTextColor = DarkOnSurface
                    ),
                    singleLine = true
                )

                // Send Button
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val userText = inputText.trim()
                            inputText = ""
                            messages.add(ConversationMessage(sender = MessageSender.USER, text = userText))

                            faceController.setExpression(FaceExpression.THINKING)

                            coroutineScope.launch {
                                val result = aiEngine.sendTextMessage(
                                    userMessage = userText,
                                    systemPersonality = settings.personalityPrompt,
                                    modelName = settings.geminiModel
                                )
                                when (result) {
                                    is AiResponseResult.Success -> {
                                        messages.add(ConversationMessage(sender = MessageSender.ROBOT, text = result.reply))
                                        faceController.setExpression(FaceExpression.SPEAKING)
                                        voiceEngine?.speak(result.reply, settings.speechRate)
                                    }
                                    is AiResponseResult.Error -> {
                                        messages.add(ConversationMessage(sender = MessageSender.SYSTEM, text = "Error: ${result.message}"))
                                        faceController.setExpression(FaceExpression.CONFUSED)
                                    }
                                    else -> {}
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkPrimaryContainer)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = DarkOnPrimary)
                }
            }
        }

        // Right Column: AI Model Info & Telemetry Cards (30% width)
        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("AI Engine Details", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Model: ${settings.geminiModel}", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    Text("Safety: Enterprise Strict", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    Text("Status: Online & Ready", color = SemanticSuccess, fontSize = 12.sp)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Voice Controls", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Voice Profile: ${settings.voiceName}", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    Text("Speed Rate: ${settings.speechRate}x", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ConversationMessage) {
    val isUser = message.sender == MessageSender.USER
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(if (isUser) DarkPrimaryContainer else DarkSurfaceContainerHigh)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = message.text,
                color = if (isUser) DarkOnPrimary else DarkOnSurface,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}
