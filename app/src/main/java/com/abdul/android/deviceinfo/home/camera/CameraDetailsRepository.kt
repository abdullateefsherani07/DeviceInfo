package com.abdul.android.deviceinfo.home.camera

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CameraDetailsRepository(private val context: Context) {
    suspend fun getFrontCameraDetails(): List<UserDeviceDetailsProperty?> {
        return withContext(Dispatchers.IO) {
            val cameraDetails = mutableListOf<UserDeviceDetailsProperty?>(null)
            try {
                val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                val cameraIds = cameraManager.cameraIdList
                var frontCameraId: String? = null
                for (id in cameraIds) {
                    val characteristics = cameraManager.getCameraCharacteristics(id)
                    val lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
                    if (lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_FRONT) {
                        frontCameraId = id
                        break
                    }
                }
                if (frontCameraId != null) {
                    val characteristics = cameraManager.getCameraCharacteristics(frontCameraId)
                    val pixelArraySize = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)
                    val megaPixelsValue = if (pixelArraySize != null) {
                        (pixelArraySize.width * pixelArraySize.height) / 1_000_000.0
                    } else { 0.0 }
                    val megaPixels = UserDeviceDetailsProperty("Mega Pixels", megaPixelsValue.toString())
                    cameraDetails.add(megaPixels)
                    val aberrationModesValue = characteristics.get(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES)
                    aberrationModesValue?.let {
                        val aberrationModesNames = it.map { mode ->
                            when (mode) {
                                CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_OFF -> "OFF"
                                CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_FAST -> "Fast"
                                CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY -> "High Quality"
                                else -> "Unknown"
                            }
                        }
                        val aberrationModes = UserDeviceDetailsProperty("Aberration Modes", aberrationModesNames.joinToString())
                        cameraDetails.add(aberrationModes)
                    }
                    val antibandingModesValue = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES)
                    antibandingModesValue?.let {
                        val antibandingModesNames = it.map { mode ->
                            when (mode) {
                                CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_OFF -> "Off"
                                CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_AUTO -> "Auto"
                                CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_50HZ -> "50Hz"
                                CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_60HZ -> "60Hz"
                                else -> "Unknown"
                            }
                        }
                        val antibandingModes = UserDeviceDetailsProperty("Antibanding Modes", antibandingModesNames.joinToString())
                        cameraDetails.add(antibandingModes)
                    }
                    val autoExposureModesValue = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)
                    autoExposureModesValue?.let {
                        val autoExposureModesNames = it.map { mode ->
                            when (mode) {
                                CameraCharacteristics.CONTROL_AE_MODE_ON -> "On"
                                CameraCharacteristics.CONTROL_AE_MODE_OFF -> "Off"
                                CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH -> "Auto Flash"
                                CameraCharacteristics.CONTROL_AE_MODE_ON_ALWAYS_FLASH -> "Always Flash"
                                CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE -> "Auto Flash Red Eye"
                                CameraCharacteristics.CONTROL_AE_MODE_ON_EXTERNAL_FLASH -> "External Flash"
                                else -> "Unknown"
                            }
                        }
                        val autoExposureModes = UserDeviceDetailsProperty("Auto Exposure Modes", autoExposureModesNames.joinToString())
                        cameraDetails.add(autoExposureModes)
                    }
                    val autoFocusModesValue = characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)
                    autoFocusModesValue?.let {
                        val autoFocusModesNames = it.map { mode ->
                            when (mode) {
                                CameraCharacteristics.CONTROL_AF_MODE_OFF -> "Off"
                                CameraCharacteristics.CONTROL_AF_MODE_AUTO -> "Auto"
                                CameraCharacteristics.CONTROL_AF_MODE_EDOF -> "Digital"
                                CameraCharacteristics.CONTROL_AF_MODE_MACRO -> "Macro"
                                CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_PICTURE -> "Continuous Picture"
                                CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_VIDEO -> "Continuous Video"
                                else -> "Unknown"
                            }
                        }
                        val autoFocusModes = UserDeviceDetailsProperty("Auto Focus Modes", autoFocusModesNames.joinToString())
                        cameraDetails.add(autoFocusModes)
                    }
                }
                cameraDetails
            } catch (e: Exception) {
                cameraDetails
            }
        }
    }
}