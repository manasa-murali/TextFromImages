package com.example.textfromimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.textfromimages.databinding.FragmentTextRecognitionBinding
import com.example.textfromimages.viewmodel.CameraViewModel
import com.example.textfromimages.viewmodel.UIState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TextRecognitionFragment : Fragment(R.layout.fragment_text_recognition) {

    @Inject
    lateinit var textRecognizer: TextRecognizer
    private val cameraViewModel: CameraViewModel by activityViewModels()
    lateinit var binding: FragmentTextRecognitionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTextRecognitionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentState = cameraViewModel.dataFlow.value
        if (currentState.uiState is UIState.Success) {
            binding.capturedImage.setImageBitmap(currentState.uiState.bitmap)
            val image = InputImage.fromBitmap(currentState.uiState.bitmap, 0)
            lifecycleScope.launch(Dispatchers.Main) {
                textRecognizer.process(image).addOnSuccessListener {
                    binding.recogText.text = it.text
                }
            }

        }
    }


}