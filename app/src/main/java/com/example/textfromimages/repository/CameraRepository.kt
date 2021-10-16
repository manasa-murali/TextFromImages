package com.example.textfromimages.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.ImageProxy

class CameraRepository : Repository {

    override suspend fun convertImageToBitmap(imageProxy: ImageProxy): Bitmap? {
        //https://stackoverflow.com/questions/56772967/converting-imageproxy-to-bitmap
        val buffer = imageProxy.planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    override fun processTextRecognition() {

    }
}