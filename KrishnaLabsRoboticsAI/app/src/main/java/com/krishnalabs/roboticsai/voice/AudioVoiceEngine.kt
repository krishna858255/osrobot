package com.krishnalabs.roboticsai.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

enum class AudioEngineState {
    IDLE,
    LISTENING,
    PROCESSING,
    SPEAKING,
    ERROR
}

class AudioVoiceEngine(
    private val context: Context,
    private val onSpeechRecognized: (String) -> Unit,
    private val onAudioLevelChanged: (Float) -> Unit,
    private val onSpeakingStateChanged: (Boolean) -> Unit
) {
    private val _engineState = MutableStateFlow(AudioEngineState.IDLE)
    val engineState: StateFlow<AudioEngineState> = _engineState.asStateFlow()

    private var speechRecognizer: SpeechRecognizer? = null
    private var textToSpeech: TextToSpeech? = null
    private var isTtsInitialized = false

    init {
        initTts()
    }

    private fun initTts() {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
                isTtsInitialized = true
                textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _engineState.value = AudioEngineState.SPEAKING
                        onSpeakingStateChanged(true)
                    }

                    override fun onDone(utteranceId: String?) {
                        _engineState.value = AudioEngineState.IDLE
                        onSpeakingStateChanged(false)
                    }

                    override fun onError(utteranceId: String?) {
                        _engineState.value = AudioEngineState.IDLE
                        onSpeakingStateChanged(false)
                    }
                })
            }
        }
    }

    fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            _engineState.value = AudioEngineState.ERROR
            return
        }

        stopSpeaking()

        speechRecognizer?.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    _engineState.value = AudioEngineState.LISTENING
                }

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {
                    val normalized = ((rmsdB + 2f) / 12f).coerceIn(0f, 1f)
                    onAudioLevelChanged(normalized)
                }

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {
                    _engineState.value = AudioEngineState.PROCESSING
                }

                override fun onError(error: Int) {
                    _engineState.value = AudioEngineState.IDLE
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val text = matches?.firstOrNull().orEmpty()
                    _engineState.value = AudioEngineState.IDLE
                    if (text.isNotBlank()) {
                        onSpeechRecognized(text)
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
        speechRecognizer?.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        _engineState.value = AudioEngineState.IDLE
    }

    fun speak(text: String, rate: Float = 1.0f) {
        if (!isTtsInitialized || text.isBlank()) return
        stopListening()
        textToSpeech?.setSpeechRate(rate)
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UTTERANCE_${System.currentTimeMillis()}")
    }

    fun stopSpeaking() {
        if (textToSpeech?.isSpeaking == true) {
            textToSpeech?.stop()
            _engineState.value = AudioEngineState.IDLE
            onSpeakingStateChanged(false)
        }
    }

    fun shutdown() {
        speechRecognizer?.destroy()
        speechRecognizer = null
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }
}
