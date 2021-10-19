package com.example.textfromimages.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.textfromimages.R
import com.example.textfromimages.databinding.FragmentCameraBinding
import com.example.textfromimages.viewmodel.CameraViewModel
import com.example.textfromimages.viewmodel.UIState
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CameraFragment : Fragment(R.layout.fragment_camera) {

    @Inject
    lateinit var imageCapture: ImageCapture

    @Inject
    lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private var binding: FragmentCameraBinding? = null
    private val TAG: String? = javaClass.canonicalName

    private val cameraViewModel: CameraViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCamera()
        binding!!.captureImage.setOnClickListener {
            takePhoto()
        }
        val currentState = cameraViewModel.dataFlow.value
        lifecycleScope.launch(Dispatchers.Main) {
            cameraViewModel.dataFlow.collect {
                if (currentState != it) {
                    when (it.uiState) {
                        is UIState.Failure -> {


                        }
                        UIState.Initial -> {

                        }
                        is UIState.Success -> {
                            if (findNavController().currentDestination!!.id == R.id.cameraFragment) {
                                findNavController().navigate(R.id.action_cameraFragment_to_textRecognitionFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun takePhoto() {

        imageCapture.takePicture(ContextCompat.getMainExecutor(requireContext()), object :
            ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                super.onCaptureSuccess(imageProxy)
                cameraViewModel.convertToBitmap(imageProxy)

            }

        }
        )

    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding!!.viewFinder.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }
}