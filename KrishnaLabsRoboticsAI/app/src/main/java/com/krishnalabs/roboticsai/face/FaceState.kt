package com.krishnalabs.roboticsai.face

import androidx.compose.ui.graphics.Color

enum class FaceExpression(val displayName: String) {
    IDLE("Idle"),
    LISTENING("Listening"),
    THINKING("Thinking"),
    SPEAKING("Speaking"),
    HAPPY("Happy"),
    CURIOUS("Curious"),
    SURPRISED("Surprised"),
    CONFUSED("Confused"),
    SLEEPY("Sleepy"),
    EXCITED("Excited")
}

data class FaceGeometry(
    val irisScaleX: Float = 1.0f,
    val irisScaleY: Float = 1.0f,
    val pupilOffsetX: Float = 0.0f,
    val pupilOffsetY: Float = 0.0f,
    val mouthCurve: Float = 0.0f, // -1.0 (frown) to +1.0 (wide smile), 0.0 is gentle neutral
    val mouthOpenRatio: Float = 0.0f,
    val showWaveform: Boolean = false,
    val eyeColor: Color = Color(0xFFADC6FF),
    val eyelidOpenRatio: Float = 1.0f // 1.0 = wide open, 0.0 = fully closed (blink)
)
