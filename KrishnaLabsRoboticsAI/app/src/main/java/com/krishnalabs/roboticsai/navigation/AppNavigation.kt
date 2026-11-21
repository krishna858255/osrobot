package com.krishnalabs.roboticsai.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.krishnalabs.roboticsai.ai.GeminiAssistantEngine
import com.krishnalabs.roboticsai.camera.CameraViewModel
import com.krishnalabs.roboticsai.data.model.RobotSettings
import com.krishnalabs.roboticsai.data.preferences.UserPreferencesRepository
import com.krishnalabs.roboticsai.face.FaceStateController
import com.krishnalabs.roboticsai.face.FullScreenFaceScreen
import com.krishnalabs.roboticsai.hardware.TabletHardwareMonitor
import com.krishnalabs.roboticsai.robotics.ExternalRoboticsBridge
import com.krishnalabs.roboticsai.ui.components.TabletScaffold
import com.krishnalabs.roboticsai.ui.screens.ActivityScreen
import com.krishnalabs.roboticsai.ui.screens.AssistantScreen
import com.krishnalabs.roboticsai.ui.screens.AutomationsScreen
import com.krishnalabs.roboticsai.ui.screens.ControlsScreen
import com.krishnalabs.roboticsai.ui.screens.HomeScreen
import com.krishnalabs.roboticsai.ui.screens.RobotFaceScreen
import com.krishnalabs.roboticsai.ui.screens.SettingsScreen
import com.krishnalabs.roboticsai.ui.screens.VisionScreen
import com.krishnalabs.roboticsai.ui.screens.settings.AboutScreen
import com.krishnalabs.roboticsai.ui.screens.settings.FaceCustomizationScreen
import com.krishnalabs.roboticsai.ui.screens.settings.GeminiSettingsScreen
import com.krishnalabs.roboticsai.ui.screens.settings.IdentitySettingsScreen
import com.krishnalabs.roboticsai.ui.screens.settings.VoiceSettingsScreen
import com.krishnalabs.roboticsai.voice.AudioVoiceEngine

@Composable
fun AppNavigation(
    navController: NavHostController,
    settings: RobotSettings,
    prefsRepo: UserPreferencesRepository,
    faceController: FaceStateController,
    cameraViewModel: CameraViewModel,
    voiceEngine: AudioVoiceEngine?,
    aiEngine: GeminiAssistantEngine,
    hardwareMonitor: TabletHardwareMonitor,
    roboticsBridge: ExternalRoboticsBridge
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route
    val hardwareStatus by hardwareMonitor.status.collectAsState()

    if (currentRoute == Screen.FullScreenFace.route) {
        // Full screen immersive canvas without tablet sidebar or chrome
        FullScreenFaceScreen(
            controller = faceController,
            onExitFullScreen = { navController.popBackStack() }
        )
    } else {
        TabletScaffold(
            currentRoute = currentRoute,
            onNavigate = { route ->
                navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            robotName = settings.robotName,
            companionMode = settings.companionMode
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        settings = settings,
                        hardwareStatus = hardwareStatus,
                        faceController = faceController,
                        onNavigate = { route -> navController.navigate(route) }
                    )
                }

                composable(Screen.Assistant.route) {
                    AssistantScreen(
                        settings = settings,
                        faceController = faceController,
                        voiceEngine = voiceEngine,
                        aiEngine = aiEngine
                    )
                }

                composable(Screen.Face.route) {
                    RobotFaceScreen(
                        settings = settings,
                        faceController = faceController,
                        onOpenFullScreen = { navController.navigate(Screen.FullScreenFace.route) }
                    )
                }

                composable(Screen.Vision.route) {
                    VisionScreen(
                        cameraViewModel = cameraViewModel,
                        aiEngine = aiEngine
                    )
                }

                composable(Screen.Controls.route) {
                    ControlsScreen(
                        hardwareMonitor = hardwareMonitor,
                        roboticsBridge = roboticsBridge
                    )
                }

                composable(Screen.Automations.route) {
                    AutomationsScreen()
                }

                composable(Screen.Activity.route) {
                    ActivityScreen()
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(onNavigate = { route -> navController.navigate(route) })
                }

                // Settings Sub-Routes
                composable(Screen.RobotIdentitySettings.route) {
                    IdentitySettingsScreen(
                        currentSettings = settings,
                        prefsRepo = prefsRepo,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.GeminiAiSettings.route) {
                    GeminiSettingsScreen(
                        currentSettings = settings,
                        prefsRepo = prefsRepo,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.VoiceSettings.route) {
                    VoiceSettingsScreen(
                        currentSettings = settings,
                        prefsRepo = prefsRepo,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.FaceCustomizationSettings.route) {
                    FaceCustomizationScreen(
                        currentSettings = settings,
                        prefsRepo = prefsRepo,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.AboutSettings.route) {
                    AboutScreen(onBack = { navController.popBackStack() })
                }

                composable(Screen.AppearanceSettings.route) {
                    AboutScreen(onBack = { navController.popBackStack() })
                }

                composable(Screen.CameraSettings.route) {
                    VisionScreen(cameraViewModel = cameraViewModel, aiEngine = aiEngine)
                }

                composable(Screen.PrivacyPermissionsSettings.route) {
                    AboutScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
