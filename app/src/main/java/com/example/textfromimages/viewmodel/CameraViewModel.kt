package com.example.textfromimages.viewmodel

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.textfromimages.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel
@Inject
constructor(
    private val cameraRepository: CameraRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(AppState(uiState = UIState.Initial))
    val dataFlow = mutableStateFlow.asStateFlow()


    fun convertToBitmap(imageProxy: ImageProxy) {
        val currentState = mutableStateFlow.value
        viewModelScope.launch(dispatcher) {
            val bitmap = cameraRepository.convertImageToBitmap(imageProxy)
            if (bitmap != null) {
                imageProxy.close()
                mutableStateFlow.emit(
                    currentState.copy(
                        uiState = (UIState.Success(bitmap))
                    )
                )

            } else {

            }


        }
    }
}