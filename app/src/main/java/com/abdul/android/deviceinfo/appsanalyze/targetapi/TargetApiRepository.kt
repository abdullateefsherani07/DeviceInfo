package com.abdul.android.deviceinfo.appsanalyze.targetapi

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class TargetApi(
    val versionCode: String,
    var api: Int,
    var totalApps: Int
)
class TargetApiRepository(private val context: Context) {

    suspend fun getTargetApiLiList(): List<StorageDetails?> {
        return withContext(Dispatchers.IO) {
            val targetApiList = mutableListOf<StorageDetails>()
            try {
                val packageManager = context.packageManager
                val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
                val totalApps = installedPackages.size

                val apiCountMap = mutableMapOf<Int, Int>()
                for(packageInfo in installedPackages){
                    val targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion
                    apiCountMap[targetSdkVersion] = (apiCountMap[targetSdkVersion] ?: 0) + 1
                }
                for ((api, count) in apiCountMap){
                    val targetApi = StorageDetails("API $api", totalApps.toLong(), count.toLong())
//                    Log.e("TargetApiRepository", "Version Name: ${targetApi.versionCode} API: ${targetApi.api}, Total Apps: ${targetApi.totalApps}")
                    targetApiList.add(targetApi)
                }
                targetApiList.sortedByDescending { it.name.replace("API ", "").toInt() }
            } catch (e: Exception) {
                Log.e("TargetApiRepository", e.message!!)
                targetApiList
            }

        }
    }
}