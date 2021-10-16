package com.example.textfromimages.viewmodel

import android.graphics.Bitmap

data class AppState(
    val uiState: UIState = UIState.Initial
)

sealed class UIState {
    object Initial : UIState()
    data class Success(val bitmap: Bitmap) : UIState()
    data class Failure(val error: String) : UIState()
}