package com.abdul.android.deviceinfo.home.device

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDeviceDetailsRepository(private var context: Context) {
    suspend fun getUserDeviceDetails(): List<UserDeviceDetailsProperty?>? {
        return withContext(Dispatchers.IO){
            val userDeviceDetails = mutableListOf<UserDeviceDetailsProperty?>(null)
            try {
                val deviceNameValue = Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME)
                val deviceName = UserDeviceDetailsProperty("Device Name", deviceNameValue)
                userDeviceDetails.add(deviceName)
                val deviceCodeName = UserDeviceDetailsProperty("Device Code", Build.DEVICE)
                userDeviceDetails.add(deviceCodeName)
                val model = UserDeviceDetailsProperty("Model",Build.MODEL )
                userDeviceDetails.add(model)
                val manufacturer = UserDeviceDetailsProperty("Manufacturer", Build.MANUFACTURER)
                userDeviceDetails.add(manufacturer)
                val board = UserDeviceDetailsProperty("Board", Build.BOARD)
                userDeviceDetails.add(board)
                val hardware = UserDeviceDetailsProperty("Hardware", Build.HARDWARE)
                userDeviceDetails.add(hardware)
                val brand = UserDeviceDetailsProperty("Brand", Build.BRAND)
                userDeviceDetails.add(brand)
                val androidDeviceId = UserDeviceDetailsProperty("Android Device ID", Build.ID)
                userDeviceDetails.add(androidDeviceId)
                val buildFingerprint = UserDeviceDetailsProperty("Fingerprint", Build.FINGERPRINT)
                userDeviceDetails.add(buildFingerprint)

                userDeviceDetails
            } catch (e: Exception){
                Log.e("UserDeviceDetailsRepository", e.message!!)
                userDeviceDetails
            }
        }
    }
}