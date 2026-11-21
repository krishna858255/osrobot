package com.krishnalabs.roboticsai.data.model

data class RobotSettings(
    val robotName: String = "Aria-01",
    val assistantName: String = "Aria",
    val personalityPrompt: String = "You are Aria, an intelligent and empathetic robotics assistant engineered by Krishna Labs.",
    val greetingMessage: String = "Hello Krishna! Systems are operational and ready for your commands.",
    val companionMode: String = "Companion Mode", // Companion Mode, Workbench Mode, Standby Mode
    val eyeColorHex: String = "#ADC6FF",
    val eyeShape: String = "Rounded Rect",
    val animationSpeed: Float = 1.0f,
    val blinkFrequencySec: Float = 4.5f,
    val voiceEnabled: Boolean = true,
    val voiceName: String = "Aoede (Warm / Adaptive)",
    val speechRate: Float = 1.0f,
    val speechPitch: Float = 1.0f,
    val cameraFlashlightEnabled: Boolean = false,
    val selectedCameraLens: String = "Back Camera",
    val geminiModel: String = "gemini-2.0-flash",
    val geminiApiKeyConfigured: Boolean = false,
    val rosBridgeAddress: String = "ws://192.168.1.100:9090",
    val externalRoboticsConnected: Boolean = false
)

data class ConversationMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val hasImageAttachment: Boolean = false
)

enum class MessageSender {
    USER,
    ROBOT,
    SYSTEM
}

data class ActivityEvent(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val category: EventCategory,
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSuccess: Boolean = true
)

enum class EventCategory {
    ALL,
    CONVERSATION,
    CAMERA,
    AI_CONNECTION,
    VOICE,
    PERMISSIONS,
    HARDWARE,
    ERRORS
}

data class AutomationRule(
    val id: String,
    val name: String,
    val triggerDescription: String,
    val actionDescription: String,
    val isEnabled: Boolean,
    val iconName: String
)
