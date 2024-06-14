package com.abdul.android.deviceinfo.home.system

import android.content.Context
import android.opengl.GLES10
import android.os.Build
import android.util.Log
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.TimeZone

class DeviceSystemDetailsRepository(private val context: Context) {
    suspend fun getDeviceSystemDetails(): List<UserDeviceDetailsProperty?> {
        return withContext(Dispatchers.IO){
            val deviceSystemDetails = mutableListOf<UserDeviceDetailsProperty?>(null)
            try {
                val androidVersion = UserDeviceDetailsProperty("Android Version", "Android " + Build.VERSION.RELEASE)
                deviceSystemDetails.add(androidVersion)
                val codeName = UserDeviceDetailsProperty("Code Name", Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name)
                deviceSystemDetails.add(codeName)
                val apiLevel = UserDeviceDetailsProperty("API Level", Build.VERSION.SDK_INT.toString())
                deviceSystemDetails.add(apiLevel)
                val securityPatch = UserDeviceDetailsProperty("Security Patch", Build.VERSION.SECURITY_PATCH)
                deviceSystemDetails.add(securityPatch)
                val bootloader = UserDeviceDetailsProperty("Bootloader", Build.BOOTLOADER)
                deviceSystemDetails.add(bootloader)
                val buildNumber = UserDeviceDetailsProperty("Build Number", Build.DISPLAY)
                deviceSystemDetails.add(buildNumber)
                val baseband = UserDeviceDetailsProperty("Baseband", Build.getRadioVersion())
                deviceSystemDetails.add(baseband)
                val javaVm = UserDeviceDetailsProperty("Java VM", System.getProperty("java.vm.version"))
                deviceSystemDetails.add(javaVm)
                val kernel = UserDeviceDetailsProperty("Kernel", System.getProperty("os.version"))
                deviceSystemDetails.add(kernel)
                val language = UserDeviceDetailsProperty("Language", Locale.getDefault().toString())
                deviceSystemDetails.add(language)
                val timezone = UserDeviceDetailsProperty("Timezone", TimeZone.getDefault().id)
                deviceSystemDetails.add(timezone)
                val googlePlayServicesInfo = context.packageManager.getPackageInfo(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE, 0)
                val googlePlayServices = UserDeviceDetailsProperty("Google Play Services", googlePlayServicesInfo.versionName)
                deviceSystemDetails.add(googlePlayServices)
                deviceSystemDetails
            } catch (e: Exception){
                Log.e("DeviceSystemDetailsRepository", e.message!!)
                deviceSystemDetails
            }
        }
    }
}