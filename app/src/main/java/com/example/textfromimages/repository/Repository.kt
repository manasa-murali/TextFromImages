package com.example.textfromimages.repository

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy

interface Repository {

    suspend fun convertImageToBitmap(imageProxy: ImageProxy): Bitmap?
    fun processTextRecognition()

}