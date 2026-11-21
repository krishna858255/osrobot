package com.krishnalabs.roboticsai.camera

import android.content.Context
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executor

class CameraViewModel : ViewModel() {

    private val _isCameraReady = MutableStateFlow(false)
    val isCameraReady: StateFlow<Boolean> = _isCameraReady.asStateFlow()

    private val _isFlashlightOn = MutableStateFlow(false)
    val isFlashlightOn: StateFlow<Boolean> = _isFlashlightOn.asStateFlow()

    private val _useFrontCamera = MutableStateFlow(false)
    val useFrontCamera: StateFlow<Boolean> = _useFrontCamera.asStateFlow()

    private val _lastCapturedPhoto = MutableStateFlow<File?>(null)
    val lastCapturedPhoto: StateFlow<File?> = _lastCapturedPhoto.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null

    fun toggleCameraLens() {
        _useFrontCamera.value = !_useFrontCamera.value
    }

    fun toggleFlashlight() {
        val current = _isFlashlightOn.value
        camera?.cameraControl?.enableTorch(!current)
        _isFlashlightOn.value = !current
    }

    fun bindCamera(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        surfaceProvider: Preview.SurfaceProvider
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = surfaceProvider
                }

                imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                val lensFacing = if (_useFrontCamera.value) {
                    CameraSelector.LENS_FACING_FRONT
                } else {
                    CameraSelector.LENS_FACING_BACK
                }

                val selector = CameraSelector.Builder()
                    .requireLensFacing(lensFacing)
                    .build()

                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageCapture
                )

                _isCameraReady.value = true
                _errorMessage.value = null
            } catch (e: Exception) {
                _isCameraReady.value = false
                _errorMessage.value = "Failed to initialize Camera: ${e.localizedMessage}"
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun capturePhoto(context: Context, onPhotoCaptured: (File) -> Unit) {
        val capture = imageCapture ?: run {
            _errorMessage.value = "Image capture not ready"
            return
        }

        val photoFile = File(
            context.cacheDir,
            "robot_vision_${System.currentTimeMillis()}.jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        capture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    _lastCapturedPhoto.value = photoFile
                    onPhotoCaptured(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    _errorMessage.value = "Failed to capture image: ${exception.message}"
                }
            }
        )
    }
}
