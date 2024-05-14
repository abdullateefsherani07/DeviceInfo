package com.abdul.android.deviceinfo.home.display

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.pow

class DisplayDetailsRepository(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDisplayDetails(): List<UserDeviceDetailsProperty?> {
        return withContext(Dispatchers.IO) {
            val displayDetails = mutableListOf<UserDeviceDetailsProperty?>(null)
            try {
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

                val display = windowManager.defaultDisplay
                val metrics = DisplayMetrics()
                display.getMetrics(metrics)

                val resolutionValue = "${metrics.widthPixels} x ${metrics.heightPixels} Pixels"
                val resolution = UserDeviceDetailsProperty("Resolution", resolutionValue)
                displayDetails.add(resolution)

                val densityValue = "${metrics.densityDpi} dpi"
                val density = UserDeviceDetailsProperty("Density", densityValue)
                displayDetails.add(density)

                val fontScale = UserDeviceDetailsProperty("Font Scale", metrics.scaledDensity.toString())
                displayDetails.add(fontScale)

                val physicalSizeValue = getDisplayPhysicalSize()
                val physicalSize = UserDeviceDetailsProperty("Physical Size", physicalSizeValue.toString())
                displayDetails.add(physicalSize)

                val refreshState = UserDeviceDetailsProperty("Refresh Rate", display.refreshRate.toString())
                displayDetails.add(refreshState)

                val brightnessLevelValue = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
                val brightnessLevel = UserDeviceDetailsProperty("Brightness Level", "$brightnessLevelValue (${((brightnessLevelValue/255)*100)}%)")
                displayDetails.add(brightnessLevel)

                val brightnessModeValue = when(Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)) {
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC -> "Automatic"
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL -> "Manual"
                    else -> "Unknown"
                }
                val brightnessMode = UserDeviceDetailsProperty("Brightness Mode", brightnessModeValue)
                displayDetails.add(brightnessMode)

                val hdrValue = if (display.isHdr) "Supported" else "Not Supported"
                val hdr = UserDeviceDetailsProperty("HDR", hdrValue)
                displayDetails.add(hdr)

                val hdrCapabilitiesValue = display.hdrCapabilities
                val hdrMaxLuminance = hdrCapabilitiesValue.desiredMaxLuminance
                val hdrMaxAverageLuminance = hdrCapabilitiesValue.desiredMaxAverageLuminance
                val hdrMinLuminance = hdrCapabilitiesValue.desiredMinLuminance
                val hdrCapabilitiesString = """
                    |Max Luminance: $hdrMaxLuminance
                    |Max Average Luminance: $hdrMaxAverageLuminance
                    |Min Luminance: $hdrMinLuminance
                """.trimMargin()
                val hdrCapabilities = UserDeviceDetailsProperty("HDR Capabilities", hdrCapabilitiesString)
                displayDetails.add(hdrCapabilities)

                val screenTimeoutValue = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)
                val screenTimeout = UserDeviceDetailsProperty("Screen Timeout", "${screenTimeoutValue/1000} Seconds")
                displayDetails.add(screenTimeout)

                val orientationValue = when (context.resources.configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> "Portrait"
                    Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
                    else -> "Unspecified"
                }
                val orientation = UserDeviceDetailsProperty("Orientation", orientationValue)
                displayDetails.add(orientation)

                displayDetails
            } catch (e: Exception) {
                displayDetails
            }
        }
    }

    private fun getDisplayPhysicalSize(): String {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels

        val xdpi = displayMetrics.xdpi
        val ydpi = displayMetrics.ydpi

        val screenWidthInches = widthPixels / xdpi
        val screenHeightInches = heightPixels / ydpi

        return "%.1f Inches".format(
            kotlin.math.sqrt((screenWidthInches * screenWidthInches) + (screenHeightInches * screenHeightInches))
        )
    }

}