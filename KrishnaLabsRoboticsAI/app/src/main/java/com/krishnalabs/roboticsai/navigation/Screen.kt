package com.krishnalabs.roboticsai.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Assistant : Screen("assistant", "AI Assistant")
    object Face : Screen("face", "Robot Face")
    object FullScreenFace : Screen("full_screen_face", "Full-Screen Face")
    object Vision : Screen("vision", "Vision")
    object Controls : Screen("controls", "Controls")
    object Automations : Screen("automations", "Automations")
    object Activity : Screen("activity", "Activity")
    object Settings : Screen("settings", "Settings")
    
    // Settings Sub-Screens
    object RobotIdentitySettings : Screen("settings_robot_identity", "Robot Identity")
    object GeminiAiSettings : Screen("settings_gemini_ai", "Gemini AI")
    object VoiceSettings : Screen("settings_voice", "Voice")
    object AppearanceSettings : Screen("settings_appearance", "Appearance")
    object FaceCustomizationSettings : Screen("settings_face_customization", "Face Customization")
    object CameraSettings : Screen("settings_camera", "Camera Settings")
    object PrivacyPermissionsSettings : Screen("settings_privacy_permissions", "Privacy & Permissions")
    object AboutSettings : Screen("settings_about", "About Application")
    object Diagnostics : Screen("settings_diagnostics", "Diagnostics")
}
