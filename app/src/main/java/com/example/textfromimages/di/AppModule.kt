package com.example.textfromimages.di

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesTextRecognizer(): TextRecognizer {
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    @Provides
    @Singleton
    fun providesImageCapture(): ImageCapture {
        return ImageCapture.Builder()
            .also {
                it.setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            }
            .build()
    }

    @Provides
    @Singleton
    fun providesCameraProvider(@ApplicationContext context: Context): ListenableFuture<ProcessCameraProvider> {
        return ProcessCameraProvider.getInstance(context)
    }


}