package com.abdul.android.deviceinfo.home

import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class DeviceDetailsRepository {
    suspend fun getDeviceDetails(block: suspend () -> List<UserDeviceDetailsProperty?>): List<UserDeviceDetailsProperty?> {
        return withContext(Dispatchers.IO) {
            val deviceDetails = block()
            try {
                deviceDetails
            } catch(e: Exception) {
                deviceDetails
            }
        }
    }
}