# Krishna Labs Robotics AI — Android Implementation

Native Android tablet application functioning as the autonomous computing brain for robotics companions.

---

## 🛠️ Architecture & Specifications

- **Device Format:** Android Tablet Landscape ($1280 \times 800$, $1920 \times 1200$)
- **Operating System:** Android 9.0+ (API 28 – API 35)
- **Language:** Kotlin 2.1.0
- **UI Framework:** Jetpack Compose (Material 3 Dark Theme according to Stitch specifications)
- **State Management:** MVVM with Kotlin Coroutines & StateFlow
- **Storage & Settings:** Encrypted AndroidX DataStore Preferences

---

## 📦 Implemented Modules

1. **Core Application & Navigation:**
   - 220dp fixed sidebar navigation (Home, AI Assistant, Robot Face, Vision, Controls, Automations, Activity, Settings).
   - High-contrast Stitch dark palette (`#10131A` background, `#ADC6FF` primary, `#1D2027` containers).

2. **Robot Face Canvas Engine:**
   - Vector Canvas rendering (head boundary, recessed eye sockets, iris glow, tracking pupil with specular reflection, animated mouth bezier curve, voice waveform equalizer).
   - 10 distinct expressions (`Idle`, `Listening`, `Thinking`, `Speaking`, `Happy`, `Curious`, `Surprised`, `Confused`, `Sleepy`, `Excited`).
   - Natural blinking loop ($3.5s - 5.5s$ random intervals with double-blink physics) and organic micro-gaze shifting.
   - Immersive full-screen tablet mode.

3. **CameraX Vision Subsystem:**
   - Real-time hardware preview with lens switching (Front / Back).
   - Flashlight / torch controls.
   - High-resolution frame capture integrated with multimodal Gemini AI scene inspection.

4. **Voice & Speech Engine:**
   - Native Android `SpeechRecognizer` listener with dynamic RMS audio level feedback mapped to the robot face.
   - Android `TextToSpeech` synthesis with adjustable rate, pitch, and synchronized face mouth movements.

5. **Gemini AI Integration:**
   - Multimodal text and visual scene comprehension.
   - Configurable model endpoint selection (`gemini-2.0-flash`, `gemini-1.5-pro`).
   - Secure Android Keystore / DataStore credential storage architecture (zero hardcoded keys).

6. **Robot Personalization & Identity:**
   - Robot naming, companion mode toggling, personality prompt instructions, custom greeting speech.
   - Iris color customization palette with real-time visual feedback.

7. **Tablet Hardware Telemetry:**
   - Live battery level and charging state monitoring via broadcast receivers.
   - Wi-Fi, Ethernet, and cellular connection detection.
   - System media volume stream control.

8. **External Robotics Bridge:**
   - Isolated ROS 2 / rosbridge WebSocket architecture.
   - Hardware status reporting for external actuators, Raspberry Pi coprocessors, and LiDAR sensors.

---

## 🚀 Building & Generating APK

### Requirements
- Android Studio Ladybug | 2024.2.1 or newer
- JDK 17 or JDK 21
- Android SDK Platform 35

### Steps
1. Open Android Studio.
2. Select **Open an existing project** and navigate to:
   ```
   c:\Users\Krishna Mohite\Documents\osantrigivity\KrishnaLabsRoboticsAI
   ```
3. Allow Gradle to synchronize dependencies.
4. To build debug APK from Android Studio:
   - Menu: `Build` > `Build Bundle(s) / APK(s)` > `Build APK(s)`.
   - Output will be generated at: `app/build/outputs/apk/debug/app-debug.apk`.
5. Alternatively, run via terminal:
   ```bash
   ./gradlew assembleDebug
   ```
