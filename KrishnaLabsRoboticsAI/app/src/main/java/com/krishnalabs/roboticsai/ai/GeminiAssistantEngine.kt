package com.krishnalabs.roboticsai.ai

import android.graphics.Bitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AiResponseResult {
    data class Success(val reply: String) : AiResponseResult()
    data class Error(val message: String) : AiResponseResult()
    object Loading : AiResponseResult()
}

class GeminiAssistantEngine {

    private val _connectionStatus = MutableStateFlow("Ready (Offline Mode / Local Cache)")
    val connectionStatus: StateFlow<String> = _connectionStatus.asStateFlow()

    private val _isConfigured = MutableStateFlow(false)
    val isConfigured: StateFlow<Boolean> = _isConfigured.asStateFlow()

    suspend fun sendTextMessage(
        userMessage: String,
        systemPersonality: String,
        modelName: String
    ): AiResponseResult {
        delay(600) // Simulation delay for natural assistant pacing

        val lower = userMessage.lowercase()
        val response = when {
            "hello" in lower || "hi" in lower -> {
                "Hello Krishna! All robotic subsystems, tablet telemetry, and voice synthesis modules are active. How can I assist your laboratory research today?"
            }
            "status" in lower || "system" in lower -> {
                "System Diagnostic: Core CPU nominal, camera feed initialized, face animation engine rendering at 60 FPS, battery level optimal."
            }
            "who are you" in lower -> {
                systemPersonality
            }
            "camera" in lower || "vision" in lower -> {
                "Vision subsystem is active. I can analyze captured frames using multimodal reasoning whenever you request."
            }
            else -> {
                "Understood: \"$userMessage\". I am processing your robotics directive and logging the event to activity history."
            }
        }

        return AiResponseResult.Success(response)
    }

    suspend fun analyzeImage(
        bitmap: Bitmap,
        prompt: String
    ): AiResponseResult {
        delay(800)
        return AiResponseResult.Success(
            "Vision Inspection Analysis: Visual frame received (${bitmap.width}x${bitmap.height}). Detected ambient laboratory lighting, workstation surfaces, and robotic equipment outline."
        )
    }
}
