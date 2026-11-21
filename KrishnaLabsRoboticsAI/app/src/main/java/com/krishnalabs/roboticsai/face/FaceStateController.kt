package com.krishnalabs.roboticsai.face

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class FaceStateController : ViewModel() {

    private val _currentExpression = MutableStateFlow(FaceExpression.IDLE)
    val currentExpression: StateFlow<FaceExpression> = _currentExpression.asStateFlow()

    private val _faceGeometry = MutableStateFlow(getGeometryFor(FaceExpression.IDLE))
    val faceGeometry: StateFlow<FaceGeometry> = _faceGeometry.asStateFlow()

    private val _isBlinking = MutableStateFlow(false)
    val isBlinking: StateFlow<Boolean> = _isBlinking.asStateFlow()

    private val _eyeColor = MutableStateFlow(Color(0xFFADC6FF))
    val eyeColor: StateFlow<Color> = _eyeColor.asStateFlow()

    private val _animationSpeed = MutableStateFlow(1.0f)

    init {
        startBlinkLoop()
        startIdleBreathingGazeLoop()
    }

    fun setEyeColor(color: Color) {
        _eyeColor.value = color
        updateCurrentGeometry()
    }

    fun setAnimationSpeed(speed: Float) {
        _animationSpeed.value = speed.coerceIn(0.2f, 3.0f)
    }

    fun setExpression(expression: FaceExpression) {
        _currentExpression.value = expression
        updateCurrentGeometry()
    }

    private fun updateCurrentGeometry() {
        val base = getGeometryFor(_currentExpression.value)
        _faceGeometry.value = base.copy(eyeColor = _eyeColor.value)
    }

    private fun startBlinkLoop() {
        viewModelScope.launch {
            while (isActive) {
                // Natural random intervals between 3.2s and 5.8s adjusted by animation speed
                val delayTime = (Random.nextFloat() * 2600 + 3200) / _animationSpeed.value
                delay(delayTime.toLong())

                // Perform quick realistic blink (close for ~120ms)
                _isBlinking.value = true
                delay((120 / _animationSpeed.value).toLong())
                _isBlinking.value = false

                // Occasional natural double-blink (20% chance)
                if (Random.nextFloat() < 0.20f) {
                    delay((80 / _animationSpeed.value).toLong())
                    _isBlinking.value = true
                    delay((100 / _animationSpeed.value).toLong())
                    _isBlinking.value = false
                }
            }
        }
    }

    private fun startIdleBreathingGazeLoop() {
        viewModelScope.launch {
            while (isActive) {
                delay(4000)
                if (_currentExpression.value == FaceExpression.IDLE) {
                    // Micro gaze shift (natural organic subtle eye tracking)
                    val microGazeX = (Random.nextFloat() - 0.5f) * 6.0f
                    val microGazeY = (Random.nextFloat() - 0.5f) * 4.0f
                    val currentGeo = _faceGeometry.value
                    _faceGeometry.value = currentGeo.copy(
                        pupilOffsetX = microGazeX,
                        pupilOffsetY = microGazeY
                    )
                }
            }
        }
    }

    fun onSpeakingAudioLevel(level: Float) {
        if (_currentExpression.value == FaceExpression.SPEAKING) {
            val clamped = level.coerceIn(0.1f, 1.0f)
            val currentGeo = _faceGeometry.value
            _faceGeometry.value = currentGeo.copy(
                mouthOpenRatio = clamped
            )
        }
    }

    companion object {
        fun getGeometryFor(expression: FaceExpression): FaceGeometry {
            return when (expression) {
                FaceExpression.IDLE -> FaceGeometry(
                    irisScaleX = 1.0f,
                    irisScaleY = 1.0f,
                    pupilOffsetX = 0.0f,
                    pupilOffsetY = 0.0f,
                    mouthCurve = 0.2f,
                    mouthOpenRatio = 0.0f,
                    showWaveform = false
                )
                FaceExpression.LISTENING -> FaceGeometry(
                    irisScaleX = 1.12f,
                    irisScaleY = 1.12f,
                    pupilOffsetX = 0.0f,
                    pupilOffsetY = -4.0f,
                    mouthCurve = 0.3f,
                    mouthOpenRatio = 0.05f,
                    showWaveform = false
                )
                FaceExpression.THINKING -> FaceGeometry(
                    irisScaleX = 0.95f,
                    irisScaleY = 0.88f,
                    pupilOffsetX = 12.0f,
                    pupilOffsetY = -10.0f,
                    mouthCurve = -0.1f,
                    mouthOpenRatio = 0.0f,
                    showWaveform = false
                )
                FaceExpression.SPEAKING -> FaceGeometry(
                    irisScaleX = 1.05f,
                    irisScaleY = 1.05f,
                    pupilOffsetX = 0.0f,
                    pupilOffsetY = 0.0f,
                    mouthCurve = 0.4f,
                    mouthOpenRatio = 0.6f,
                    showWaveform = true
                )
                FaceExpression.HAPPY -> FaceGeometry(
                    irisScaleX = 1.08f,
                    irisScaleY = 0.72f,
                    pupilOffsetX = 0.0f,
                    pupilOffsetY = 4.0f,
                    mouthCurve = 0.9f,
                    mouthOpenRatio = 0.4f,
                    showWaveform = false
                )
                FaceExpression.CURIOUS -> FaceGeometry(
                    irisScaleX = 1.12f,
                    irisScaleY = 1.12f,
                    pupilOffsetX = 8.0f,
                    pupilOffsetY = -6.0f,
                    mouthCurve = 0.35f,
                    mouthOpenRatio = 0.1f,
                    showWaveform = false
                )
                FaceExpression.SURPRISED -> FaceGeometry(
                    irisScaleX = 1.25f,
                    irisScaleY = 1.25f,
                    pupilOffsetX = 0.0f,
                    pupilOffsetY = 0.0f,
                    mouthCurve = 0.0f,
                    mouthOpenRatio = 0.85f,
                    showWaveform = false
                )
                FaceExpression.CONFUSED -> FaceGeometry(
                    irisScaleX = 0.92f,
                    irisScaleY = 0.92f,
                    pupilOffsetX = -10.0f,
                    pupilOffsetY = 4.0f,
                    mouthCurve = -0.4f,
                    mouthOpenRatio = 0.05f,
                    showWaveform = false
                )
                FaceExpression.SLEEPY -> FaceGeometry(
                    irisScaleX = 1.0f,
                    irisScaleY = 0.38f,
                    pupilOffsetX = 0.0f,
                    pupilOffsetY = 8.0f,
                    mouthCurve = 0.1f,
                    mouthOpenRatio = 0.0f,
                    showWaveform = false
                )
                FaceExpression.EXCITED -> FaceGeometry(
                    irisScaleX = 1.18f,
                    irisScaleY = 1.08f,
                    pupilOffsetX = 0.0f,
                    pupilOffsetY = -3.0f,
                    mouthCurve = 1.0f,
                    mouthOpenRatio = 0.7f,
                    showWaveform = true
                )
            }
        }
    }
}
