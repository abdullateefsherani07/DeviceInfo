package com.abdul.android.deviceinfo.home.cpu

import android.os.Build
import android.util.Log
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceProcessorDetailsRepository() {

    suspend fun getProcessorDetails(): List<UserDeviceDetailsProperty?>{
        return withContext(Dispatchers.IO){
            val processorDetails = mutableListOf<UserDeviceDetailsProperty?>(null)
            try {
                val processorName = UserDeviceDetailsProperty("Processor", Build.HARDWARE)
                processorDetails.add(processorName)
                val cpuArchitecture = UserDeviceDetailsProperty("CPU Architecture", Build.SUPPORTED_ABIS[0])
                processorDetails.add(cpuArchitecture)
                val supportedABIs = UserDeviceDetailsProperty("Supported ABIs", Build.SUPPORTED_ABIS.joinToString())
                processorDetails.add(supportedABIs)
                val cpuTypeValue = if(Build.SUPPORTED_64_BIT_ABIS.isNotEmpty()) "64 Bit" else  "32 Bit"
                val cpuType = UserDeviceDetailsProperty("CPU Type", cpuTypeValue)
                processorDetails.add(cpuType)
                val cpuGovernor = UserDeviceDetailsProperty("CPU Governor", readCpuInfoProperty("ro.cpufreq.governor"))
                processorDetails.add(cpuGovernor)
                val totalCores = UserDeviceDetailsProperty("Total Cores", Runtime.getRuntime().availableProcessors().toString())
                processorDetails.add(totalCores)
                val cpuFrequency = UserDeviceDetailsProperty("CPU Frequency", readCpuInfoProperty("ro.cpufreq.frequency"))
                processorDetails.add(cpuFrequency)
                val gpuRenderer = UserDeviceDetailsProperty("GPU Renderer", readCpuInfoProperty("ro.hardware.gpu.renderer"))
                processorDetails.add(gpuRenderer)
                val gpuVendor = UserDeviceDetailsProperty("GPU Vendor", readCpuInfoProperty("ro.hardware.gpu.vendor"))
                processorDetails.add(gpuVendor)
                val gpuVersion = UserDeviceDetailsProperty("GPU Version", readCpuInfoProperty("ro.hardware.gpu.version"))
                processorDetails.add(gpuVersion) 

                processorDetails
            } catch (e: Exception){
                Log.e("DeviceProcessorDetailsRepository", e.message!!)
                processorDetails
            }
        }
    }

    private fun readCpuInfoProperty(property: String): String {
        return try {
            ProcessBuilder("getprop", property)
                .redirectErrorStream(true)
                .start()
                .inputStream
                .bufferedReader()
                .use { it.readText().trim() }
        } catch (e: Exception){
            "N/A"
        }
    }

}