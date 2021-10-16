package com.example.textfromimages.di

import com.example.textfromimages.repository.CameraRepository
import com.example.textfromimages.viewmodel.CameraViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideVM(
        cameraRepository: CameraRepository
    ): CameraViewModel {
        return CameraViewModel(cameraRepository)
    }

    @Provides
    @ViewModelScoped
    fun providesRepository(): CameraRepository {
        return CameraRepository()
    }
}