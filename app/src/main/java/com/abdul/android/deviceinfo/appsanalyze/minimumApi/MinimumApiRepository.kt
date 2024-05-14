package com.abdul.android.deviceinfo.appsanalyze.minimumApi

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MinimumApiRepository(private val context: Context) {

    suspend fun getMinimumApiList(): List<StorageDetails?> {
        return withContext(Dispatchers.IO){
            val minimumApiList = mutableListOf<StorageDetails>()
            try {
                val packageManager = context.packageManager
                val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
                val totalApps = installedPackages.size

                val apiCountMap = mutableMapOf<Int, Int>()
                for (packageInfo in installedPackages){
                    val minimumSdkVersion = packageInfo.applicationInfo.minSdkVersion
                    apiCountMap[minimumSdkVersion] = (apiCountMap[minimumSdkVersion] ?: 0) + 1
                }
                for ((api, count) in apiCountMap){
                    val minimumApi = StorageDetails("API $api", totalApps.toLong(), count.toLong())
                    minimumApiList.add(minimumApi)
                }
                minimumApiList.sortedByDescending { it.name.replace("API ", "").toInt() }
            } catch (e: Exception)  {
                Log.e("MinimumApiRepository", e.message!!)
                minimumApiList
            }
        }
    }

}