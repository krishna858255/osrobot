package com.krishnalabs.roboticsai

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.krishnalabs.roboticsai.ai.GeminiAssistantEngine
import com.krishnalabs.roboticsai.camera.CameraViewModel
import com.krishnalabs.roboticsai.data.model.RobotSettings
import com.krishnalabs.roboticsai.data.preferences.UserPreferencesRepository
import com.krishnalabs.roboticsai.face.FaceExpression
import com.krishnalabs.roboticsai.face.FaceStateController
import com.krishnalabs.roboticsai.hardware.TabletHardwareMonitor
import com.krishnalabs.roboticsai.navigation.AppNavigation
import com.krishnalabs.roboticsai.robotics.ExternalRoboticsBridge
import com.krishnalabs.roboticsai.ui.theme.KrishnaLabsRoboticsAITheme
import com.krishnalabs.roboticsai.voice.AudioVoiceEngine

class MainActivity : ComponentActivity() {

    private val faceController: FaceStateController by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()
    private lateinit var prefsRepo: UserPreferencesRepository
    private lateinit var hardwareMonitor: TabletHardwareMonitor
    private val roboticsBridge = ExternalRoboticsBridge()
    private val aiEngine = GeminiAssistantEngine()
    private var voiceEngine: AudioVoiceEngine? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val audioGranted = permissions[Manifest.permission.RECORD_AUDIO] ?: false
        if (audioGranted && voiceEngine == null) {
            setupVoiceEngine()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefsRepo = UserPreferencesRepository(applicationContext)
        hardwareMonitor = TabletHardwareMonitor(applicationContext).apply {
            startMonitoring()
        }

        checkAndRequestPermissions()

        setContent {
            KrishnaLabsRoboticsAITheme {
                val navController = rememberNavController()
                val settings by prefsRepo.settingsFlow.collectAsState(initial = RobotSettings())

                AppNavigation(
                    navController = navController,
                    settings = settings,
                    prefsRepo = prefsRepo,
                    faceController = faceController,
                    cameraViewModel = cameraViewModel,
                    voiceEngine = voiceEngine,
                    aiEngine = aiEngine,
                    hardwareMonitor = hardwareMonitor,
                    roboticsBridge = roboticsBridge
                )
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.RECORD_AUDIO)
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            setupVoiceEngine()
        }
    }

    private fun setupVoiceEngine() {
        voiceEngine = AudioVoiceEngine(
            context = applicationContext,
            onSpeechRecognized = { text ->
                // Hooked into assistant flow
            },
            onAudioLevelChanged = { level ->
                faceController.onSpeakingAudioLevel(level)
            },
            onSpeakingStateChanged = { isSpeaking ->
                if (isSpeaking) {
                    faceController.setExpression(FaceExpression.SPEAKING)
                } else {
                    faceController.setExpression(FaceExpression.IDLE)
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        hardwareMonitor.stopMonitoring()
        voiceEngine?.shutdown()
    }
}
