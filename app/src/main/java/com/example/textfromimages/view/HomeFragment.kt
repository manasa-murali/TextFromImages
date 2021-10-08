package com.example.textfromimages.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.textfromimages.R
import com.example.textfromimages.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null
    private lateinit var requestCameraPermission: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestCameraPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {

            } else {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.fromCamera.setOnClickListener {
            openCamera()
        }
        binding!!.fromStorage.setOnClickListener {

        }
    }

    private fun openCamera() {
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (CAMERA_PERMISSIONS.allPermissionsGranted()) {
            //start Camera
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)
            }
        } else {
            requestCameraPermission.launch(CAMERA_PERMISSIONS[0])
        }
    }


    private fun Array<String>.allPermissionsGranted(): Boolean {
        return all {
            ContextCompat.checkSelfPermission(
                requireContext(), it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private val CAMERA_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private val STORAGE_PERMISSIONS = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}