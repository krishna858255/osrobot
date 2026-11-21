package com.krishnalabs.roboticsai.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.krishnalabs.roboticsai.data.model.RobotSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "robot_preferences")

class UserPreferencesRepository(private val context: Context) {

    private object Keys {
        val ROBOT_NAME = stringPreferencesKey("robot_name")
        val ASSISTANT_NAME = stringPreferencesKey("assistant_name")
        val PERSONALITY = stringPreferencesKey("personality_prompt")
        val GREETING = stringPreferencesKey("greeting_message")
        val MODE = stringPreferencesKey("companion_mode")
        val EYE_COLOR = stringPreferencesKey("eye_color_hex")
        val ANIM_SPEED = floatPreferencesKey("anim_speed")
        val VOICE_ENABLED = booleanPreferencesKey("voice_enabled")
        val VOICE_NAME = stringPreferencesKey("voice_name")
        val SPEECH_RATE = floatPreferencesKey("speech_rate")
        val GEMINI_MODEL = stringPreferencesKey("gemini_model")
        val GEMINI_API_KEY = stringPreferencesKey("gemini_api_key")
        val ROS_BRIDGE = stringPreferencesKey("ros_bridge_address")
    }

    val settingsFlow: Flow<RobotSettings> = context.dataStore.data.map { prefs ->
        RobotSettings(
            robotName = prefs[Keys.ROBOT_NAME] ?: "Aria-01",
            assistantName = prefs[Keys.ASSISTANT_NAME] ?: "Aria",
            personalityPrompt = prefs[Keys.PERSONALITY] ?: "You are Aria, an intelligent and empathetic robotics assistant engineered by Krishna Labs.",
            greetingMessage = prefs[Keys.GREETING] ?: "Hello Krishna! Systems are operational and ready for your commands.",
            companionMode = prefs[Keys.MODE] ?: "Companion Mode",
            eyeColorHex = prefs[Keys.EYE_COLOR] ?: "#ADC6FF",
            animationSpeed = prefs[Keys.ANIM_SPEED] ?: 1.0f,
            voiceEnabled = prefs[Keys.VOICE_ENABLED] ?: true,
            voiceName = prefs[Keys.VOICE_NAME] ?: "Aoede (Warm / Adaptive)",
            speechRate = prefs[Keys.SPEECH_RATE] ?: 1.0f,
            geminiModel = prefs[Keys.GEMINI_MODEL] ?: "gemini-2.0-flash",
            geminiApiKeyConfigured = !(prefs[Keys.GEMINI_API_KEY].isNullOrBlank()),
            rosBridgeAddress = prefs[Keys.ROS_BRIDGE] ?: "ws://192.168.1.100:9090"
        )
    }

    suspend fun updateRobotIdentity(name: String, assistant: String, personality: String, greeting: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.ROBOT_NAME] = name
            prefs[Keys.ASSISTANT_NAME] = assistant
            prefs[Keys.PERSONALITY] = personality
            prefs[Keys.GREETING] = greeting
        }
    }

    suspend fun updateMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.MODE] = mode
        }
    }

    suspend fun updateFaceSettings(colorHex: String, speed: Float) {
        context.dataStore.edit { prefs ->
            prefs[Keys.EYE_COLOR] = colorHex
            prefs[Keys.ANIM_SPEED] = speed
        }
    }

    suspend fun updateVoiceSettings(enabled: Boolean, voice: String, rate: Float) {
        context.dataStore.edit { prefs ->
            prefs[Keys.VOICE_ENABLED] = enabled
            prefs[Keys.VOICE_NAME] = voice
            prefs[Keys.SPEECH_RATE] = rate
        }
    }

    suspend fun updateGeminiConfig(model: String, apiKey: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.GEMINI_MODEL] = model
            if (apiKey.isNotBlank()) {
                prefs[Keys.GEMINI_API_KEY] = apiKey
            }
        }
    }

    val geminiApiKeyFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.GEMINI_API_KEY]
    }
}
