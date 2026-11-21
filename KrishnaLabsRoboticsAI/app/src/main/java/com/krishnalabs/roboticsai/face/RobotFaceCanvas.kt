package com.krishnalabs.roboticsai.face

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.krishnalabs.roboticsai.ui.theme.DarkOutlineVariant
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest
import com.krishnalabs.roboticsai.ui.theme.FaceHighlight
import com.krishnalabs.roboticsai.ui.theme.FaceMouthDefault
import com.krishnalabs.roboticsai.ui.theme.FacePupilDefault
import com.krishnalabs.roboticsai.ui.theme.FaceSocketDefault
import com.krishnalabs.roboticsai.ui.theme.FaceWaveform

@Composable
fun RobotFaceCanvas(
    faceGeometry: FaceGeometry,
    isBlinking: Boolean,
    modifier: Modifier = Modifier,
    drawHeadOutline: Boolean = true
) {
    // Smooth transitions for scale, translation, and curves
    val animIrisScaleX = remember { Animatable(faceGeometry.irisScaleX) }
    val animIrisScaleY = remember { Animatable(faceGeometry.irisScaleY) }
    val animPupilX = remember { Animatable(faceGeometry.pupilOffsetX) }
    val animPupilY = remember { Animatable(faceGeometry.pupilOffsetY) }
    val animMouthCurve = remember { Animatable(faceGeometry.mouthCurve) }
    val animMouthOpen = remember { Animatable(faceGeometry.mouthOpenRatio) }

    // Waveform subtle pulsing
    val waveformPulse = remember { Animatable(0.2f) }

    LaunchedEffect(faceGeometry.showWaveform) {
        if (faceGeometry.showWaveform) {
            waveformPulse.animateTo(
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(400, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        } else {
            waveformPulse.snapTo(0.2f)
        }
    }

    LaunchedEffect(faceGeometry, isBlinking) {
        val targetScaleY = if (isBlinking) 0.04f else faceGeometry.irisScaleY
        val spec = tween<Float>(durationMillis = if (isBlinking) 100 else 220, easing = FastOutSlowInEasing)

        animIrisScaleX.animateTo(faceGeometry.irisScaleX, spec)
        animIrisScaleY.animateTo(targetScaleY, spec)
        animPupilX.animateTo(faceGeometry.pupilOffsetX, spec)
        animPupilY.animateTo(faceGeometry.pupilOffsetY, spec)
        animMouthCurve.animateTo(faceGeometry.mouthCurve, spec)
        animMouthOpen.animateTo(faceGeometry.mouthOpenRatio, spec)
    }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            // Head Dimensions (responsive)
            val headWidth = canvasW * 0.88f
            val headHeight = canvasH * 0.82f
            val headLeft = (canvasW - headWidth) / 2f
            val headTop = (canvasH - headHeight) / 2f

            if (drawHeadOutline) {
                // Background Head Base plate
                drawRoundRect(
                    color = DarkSurfaceContainerLowest,
                    topLeft = Offset(headLeft, headTop),
                    size = Size(headWidth, headHeight),
                    cornerRadius = CornerRadius(34f, 34f)
                )
                // Head Border
                drawRoundRect(
                    color = DarkOutlineVariant,
                    topLeft = Offset(headLeft, headTop),
                    size = Size(headWidth, headHeight),
                    cornerRadius = CornerRadius(34f, 34f),
                    style = Stroke(width = 2.5f)
                )
            }

            // Eyes Configuration
            val eyeSpacing = headWidth * 0.28f
            val eyeCenterY = headTop + headHeight * 0.38f
            val leftEyeCenterX = (canvasW / 2f) - eyeSpacing
            val rightEyeCenterX = (canvasW / 2f) + eyeSpacing

            val socketW = headWidth * 0.26f
            val socketH = headHeight * 0.36f

            val irisBaseW = socketW * 0.74f
            val irisBaseH = socketH * 0.74f

            val currentIrisW = irisBaseW * animIrisScaleX.value
            val currentIrisH = (irisBaseH * animIrisScaleY.value).coerceAtLeast(4f)

            listOf(leftEyeCenterX, rightEyeCenterX).forEach { eyeCenterX ->
                // Socket (Dark recessed backdrop)
                drawRoundRect(
                    color = FaceSocketDefault,
                    topLeft = Offset(eyeCenterX - socketW / 2f, eyeCenterY - socketH / 2f),
                    size = Size(socketW, socketH),
                    cornerRadius = CornerRadius(22f, 22f)
                )

                // Glowing Iris (Rounded Rect)
                val irisLeft = eyeCenterX - currentIrisW / 2f
                val irisTop = eyeCenterY - currentIrisH / 2f

                drawRoundRect(
                    color = faceGeometry.eyeColor,
                    topLeft = Offset(irisLeft, irisTop),
                    size = Size(currentIrisW, currentIrisH),
                    cornerRadius = CornerRadius(16f * animIrisScaleX.value, 16f * animIrisScaleY.value)
                )

                // Pupil (Tracking offset circle inside iris)
                if (!isBlinking && animIrisScaleY.value > 0.25f) {
                    val pupilRadius = currentIrisW * 0.22f
                    val pupilCenterX = eyeCenterX + animPupilX.value
                    val pupilCenterY = eyeCenterY + animPupilY.value

                    drawCircle(
                        color = FacePupilDefault,
                        radius = pupilRadius,
                        center = Offset(pupilCenterX, pupilCenterY)
                    )

                    // Specular Highlight
                    drawCircle(
                        color = FaceHighlight.copy(alpha = 0.85f),
                        radius = pupilRadius * 0.38f,
                        center = Offset(pupilCenterX - pupilRadius * 0.35f, pupilCenterY - pupilRadius * 0.35f)
                    )
                }
            }

            // Mouth & Waveform
            val mouthCenterY = headTop + headHeight * 0.76f
            val mouthWidth = headWidth * 0.32f
            val mouthLeft = (canvasW - mouthWidth) / 2f
            val mouthRight = (canvasW + mouthWidth) / 2f

            if (faceGeometry.showWaveform) {
                // Waveform Bars when speaking
                val barCount = 7
                val totalBarWidth = mouthWidth * 0.8f
                val barSpacing = totalBarWidth / (barCount - 1)
                val barStartX = (canvasW - totalBarWidth) / 2f

                val barHeights = listOf(14f, 24f, 38f, 48f, 38f, 24f, 14f)
                for (i in 0 until barCount) {
                    val barH = barHeights[i] * (animMouthOpen.value + 0.3f * waveformPulse.value)
                    val bx = barStartX + i * barSpacing
                    drawRoundRect(
                        color = FaceWaveform,
                        topLeft = Offset(bx - 3f, mouthCenterY - barH / 2f),
                        size = Size(6f, barH),
                        cornerRadius = CornerRadius(3f, 3f)
                    )
                }
            } else {
                // Expressive Vector Mouth Curve (Quadratic Bezier)
                val curveControlY = mouthCenterY + (animMouthCurve.value * 28f)
                val mouthPath = Path().apply {
                    moveTo(mouthLeft, mouthCenterY)
                    quadraticTo(
                        canvasW / 2f,
                        curveControlY,
                        mouthRight,
                        mouthCenterY
                    )
                }

                drawPath(
                    path = mouthPath,
                    color = FaceMouthDefault,
                    style = Stroke(
                        width = 4.0f,
                        cap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                )

                // If mouth is slightly open (e.g. surprised or curious open mouth)
                if (animMouthOpen.value > 0.25f && animMouthCurve.value <= 0.2f) {
                    val openH = 26f * animMouthOpen.value
                    drawOval(
                        color = FacePupilDefault,
                        topLeft = Offset(canvasW / 2f - 14f, mouthCenterY - openH / 2f),
                        size = Size(28f, openH)
                    )
                    drawOval(
                        color = FaceMouthDefault,
                        topLeft = Offset(canvasW / 2f - 14f, mouthCenterY - openH / 2f),
                        size = Size(28f, openH),
                        style = Stroke(width = 3f)
                    )
                }
            }
        }
    }
}
